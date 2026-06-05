"""
GastApp - Reporte Final de Analisis de Datos
Proyecto Integrador - Nuevas Tecnologias
Genera HTML + PDF sin dejar archivos de imagen temporales.
"""

import requests, base64, io, os
from datetime import datetime
from collections import defaultdict

import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import warnings
warnings.filterwarnings('ignore')

from fpdf import FPDF
from fpdf.enums import XPos, YPos

# ── CONFIG ──────────────────────────────────────
BASE_URL = "http://localhost:8080/api"
ENDPOINTS = {
    "usuarios":     f"{BASE_URL}/usuarios",
    "gastos":       f"{BASE_URL}/gastos",
    "categorias":   f"{BASE_URL}/categorias",
    "metodos_pago": f"{BASE_URL}/metodos_de_pago",
}

ACCENT  = "#a3e635"
COLORS  = ["#a3e635","#38bdf8","#fb923c","#f472b6","#a78bfa","#34d399","#fbbf24","#ef4444"]
BG_CARD = "#1c2028"
TMUTED  = "#8b95a1"
TLIGHT  = "#f0f4f8"

PDF_COLORS = ['#5b8c00','#0369a1','#ea580c','#be185d','#7c3aed','#16a34a','#ca8a04']

def fmt_moneda(v):
    return f"${v:,.0f}"

# ── ESTILOS ──────────────────────────────────────

def estilo_html():
    plt.rcParams.update({
        'figure.facecolor': BG_CARD, 'axes.facecolor': BG_CARD,
        'axes.edgecolor': '#2a2f3a', 'axes.labelcolor': TMUTED,
        'xtick.color': TMUTED, 'ytick.color': TMUTED,
        'text.color': TLIGHT, 'grid.color': '#2a2f3a',
        'grid.linewidth': 0.6, 'font.family': 'sans-serif',
        'font.size': 10, 'axes.titlesize': 12,
        'axes.titleweight': 'bold', 'axes.titlecolor': TLIGHT,
    })

def estilo_pdf():
    plt.rcParams.update({
        'figure.facecolor': 'white', 'axes.facecolor': '#f8f9fa',
        'axes.edgecolor': '#dee2e6', 'axes.labelcolor': '#495057',
        'xtick.color': '#495057', 'ytick.color': '#495057',
        'text.color': '#212529', 'grid.color': '#dee2e6',
        'grid.linewidth': 0.6, 'font.family': 'sans-serif',
        'font.size': 10, 'axes.titlesize': 12,
        'axes.titleweight': 'bold', 'axes.titlecolor': '#212529',
    })

# ── UTILIDADES DE FIGURA ─────────────────────────

def fig_to_b64(fig, fondo=BG_CARD):
    buf = io.BytesIO()
    fig.savefig(buf, format='png', bbox_inches='tight', facecolor=fondo, edgecolor='none', dpi=130)
    buf.seek(0)
    b64 = base64.b64encode(buf.read()).decode('utf-8')
    plt.close(fig)
    return b64

def fig_to_bytes(fig):
    """Devuelve bytes PNG para insertar en PDF sin tocar disco."""
    buf = io.BytesIO()
    fig.savefig(buf, format='png', bbox_inches='tight', facecolor='white', edgecolor='none', dpi=120)
    buf.seek(0)
    plt.close(fig)
    return buf

# ── CARGA DE DATOS ───────────────────────────────

def cargar_datos():
    datos = {}
    for nombre, url in ENDPOINTS.items():
        try:
            r = requests.get(url, timeout=8)
            r.raise_for_status()
            raw = r.json()
            datos[nombre] = raw if isinstance(raw, list) else raw.get('content', [])
            print(f"  v {nombre}: {len(datos[nombre])} registros")
        except Exception as e:
            print(f"  x {nombre}: {e}")
            datos[nombre] = []
    return datos

# ── GRÁFICAS ─────────────────────────────────────

def g_volumen(gastos, pdf=False):
    estilo_pdf() if pdf else estilo_html()
    por_mes = defaultdict(float)
    for g in gastos:
        f = g.get('fecha','')
        if f and len(f) >= 7:
            por_mes[f[:7]] += g.get('valor', 0)
    meses   = sorted(por_mes)
    totales = [por_mes[m] for m in meses]
    labels  = [m[5:]+'/'+m[2:4] for m in meses]
    color   = '#5b8c00' if pdf else ACCENT
    fig, ax = plt.subplots(figsize=(9, 3.8))
    bars = ax.bar(labels, totales, color=color, alpha=0.88, width=0.55, zorder=3)
    ax.set_title("Volumen de Gastos por Mes", pad=12)
    ax.set_ylabel("Total (COP)")
    ax.yaxis.set_major_formatter(plt.FuncFormatter(lambda v,_: f"${v/1e6:.1f}M" if v>=1e6 else f"${v/1e3:.0f}k"))
    ax.grid(axis='y', zorder=0); ax.set_axisbelow(True)
    for bar, val in zip(bars, totales):
        ax.text(bar.get_x()+bar.get_width()/2, bar.get_height()+max(totales,default=1)*0.01,
                fmt_moneda(val), ha='center', va='bottom', fontsize=8, color=color)
    fig.tight_layout()
    return fig_to_bytes(fig) if pdf else fig_to_b64(fig)

def g_estado_gastos(gastos, pdf=False):
    estilo_pdf() if pdf else estilo_html()
    estados = defaultdict(int)
    for g in gastos:
        estados[g.get('estadoGasto','Sin estado')] += 1
    labels, valores = list(estados.keys()), list(estados.values())
    colores = PDF_COLORS[:len(labels)] if pdf else COLORS[:len(labels)]
    fig, ax = plt.subplots(figsize=(5, 4))
    _, texts, autotexts = ax.pie(valores, labels=labels, colors=colores,
        autopct='%1.1f%%', startangle=140,
        wedgeprops=dict(width=0.65, edgecolor='white', linewidth=2), pctdistance=0.78)
    for t in texts: t.set_color('#212529' if pdf else TMUTED)
    for a in autotexts: a.set_color('#212529'); a.set_fontweight('bold')
    ax.set_title("Estado de Gastos", pad=12)
    fig.tight_layout()
    return fig_to_bytes(fig) if pdf else fig_to_b64(fig)

def g_canal(gastos, pdf=False):
    estilo_pdf() if pdf else estilo_html()
    por_canal = defaultdict(float)
    for g in gastos:
        por_canal[g.get('canalCompra') or 'Sin canal'] += g.get('valor', 0)
    canales = sorted(por_canal, key=por_canal.get, reverse=True)
    valores = [por_canal[c] for c in canales]
    color   = '#0369a1' if pdf else COLORS[1]
    fig, ax = plt.subplots(figsize=(7, 3.2))
    bars = ax.barh(canales, valores, color=color, alpha=0.88, height=0.5, zorder=3)
    ax.set_title("Monto Total por Canal de Compra", pad=12)
    ax.xaxis.set_major_formatter(plt.FuncFormatter(lambda v,_: f"${v/1e6:.1f}M" if v>=1e6 else f"${v/1e3:.0f}k"))
    ax.grid(axis='x', zorder=0); ax.set_axisbelow(True)
    for bar, val in zip(bars, valores):
        ax.text(val+max(valores,default=1)*0.01, bar.get_y()+bar.get_height()/2,
                fmt_moneda(val), va='center', fontsize=8, color=color)
    fig.tight_layout()
    return fig_to_bytes(fig) if pdf else fig_to_b64(fig)

def g_top_usuarios(gastos, usuarios, pdf=False):
    estilo_pdf() if pdf else estilo_html()
    usr_map = {u['id']: u.get('nombre', f"#{u['id']}") for u in usuarios}
    por_usr = defaultdict(float)
    for g in gastos:
        uid = g.get('usuario', {})
        if isinstance(uid, dict): uid = uid.get('id')
        if uid: por_usr[uid] += g.get('valor', 0)
    top    = sorted(por_usr.items(), key=lambda x: x[1], reverse=True)[:5]
    nombres = [usr_map.get(uid, f"#{uid}") for uid,_ in top]
    montos  = [m for _,m in top]
    color   = '#ea580c' if pdf else COLORS[2]
    fig, ax = plt.subplots(figsize=(8, 3.8))
    bars = ax.bar(range(len(nombres)), montos, color=color, alpha=0.88, width=0.55, zorder=3)
    ax.set_xticks(range(len(nombres)))
    ax.set_xticklabels(nombres, rotation=15, ha='right', fontsize=9)
    ax.set_title("Top 5 Usuarios con Mayor Gasto", pad=12)
    ax.set_ylabel("Total (COP)")
    ax.yaxis.set_major_formatter(plt.FuncFormatter(lambda v,_: f"${v/1e6:.1f}M" if v>=1e6 else f"${v/1e3:.0f}k"))
    ax.grid(axis='y', zorder=0); ax.set_axisbelow(True)
    for bar, val in zip(bars, montos):
        ax.text(bar.get_x()+bar.get_width()/2, bar.get_height()+max(montos,default=1)*0.01,
                fmt_moneda(val), ha='center', va='bottom', fontsize=8, color=color)
    fig.tight_layout()
    return fig_to_bytes(fig) if pdf else fig_to_b64(fig)

def g_estado_usuarios(usuarios, pdf=False):
    estilo_pdf() if pdf else estilo_html()
    estados = defaultdict(int)
    for u in usuarios:
        estados[u.get('estadoCuenta','Desconocido')] += 1
    labels, valores = list(estados.keys()), list(estados.values())
    colores = ['#16a34a','#dc2626','#7c3aed'] if pdf else [COLORS[5],COLORS[7],COLORS[4]]
    fig, ax = plt.subplots(figsize=(5, 4))
    _, texts, autotexts = ax.pie(valores, labels=labels, colors=colores[:len(labels)],
        autopct='%1.1f%%', startangle=90,
        wedgeprops=dict(width=0.6, edgecolor='white', linewidth=2), pctdistance=0.78)
    for t in texts: t.set_color('#212529' if pdf else TMUTED)
    for a in autotexts: a.set_color('#212529'); a.set_fontweight('bold')
    ax.set_title("Estado de Usuarios", pad=12)
    fig.tight_layout()
    return fig_to_bytes(fig) if pdf else fig_to_b64(fig)

def g_tendencia(gastos, pdf=False):
    estilo_pdf() if pdf else estilo_html()
    por_mes = defaultdict(float)
    for g in gastos:
        f = g.get('fecha','')
        if f and len(f) >= 7: por_mes[f[:7]] += g.get('valor', 0)
    meses  = sorted(por_mes)
    acum   = []
    s = 0
    for m in meses:
        s += por_mes[m]; acum.append(s)
    labels = [m[5:]+'/'+m[2:4] for m in meses]
    color  = '#5b8c00' if pdf else ACCENT
    fig, ax = plt.subplots(figsize=(9, 3.5))
    ax.fill_between(range(len(meses)), acum, color=color, alpha=0.15)
    ax.plot(range(len(meses)), acum, color=color, linewidth=2.5, marker='o', markersize=5)
    ax.set_xticks(range(len(meses)))
    ax.set_xticklabels(labels, rotation=20, ha='right', fontsize=9)
    ax.set_title("Tendencia Acumulada de Gastos", pad=12)
    ax.set_ylabel("Acumulado (COP)")
    ax.yaxis.set_major_formatter(plt.FuncFormatter(lambda v,_: f"${v/1e6:.1f}M" if v>=1e6 else f"${v/1e3:.0f}k"))
    ax.grid(True, zorder=0); ax.set_axisbelow(True)
    fig.tight_layout()
    return fig_to_bytes(fig) if pdf else fig_to_b64(fig)

# ── MÉTRICAS ─────────────────────────────────────

def calcular_metricas(datos):
    gastos    = datos['gastos']
    usuarios  = datos['usuarios']
    total     = sum(g.get('valor',0) for g in gastos)
    mes_act   = datetime.now().strftime('%Y-%m')
    gasto_mes = sum(g.get('valor',0) for g in gastos if g.get('fecha','').startswith(mes_act))
    return {
        'total_usuarios':  len(usuarios),
        'usr_activos':     sum(1 for u in usuarios if u.get('estadoCuenta')=='Activo'),
        'usr_bloqueados':  sum(1 for u in usuarios if u.get('estadoCuenta')=='Bloqueado'),
        'total_gastos':    len(gastos),
        'total_gasto':     total,
        'gasto_mes':       gasto_mes,
        'prom_gasto':      total/len(gastos) if gastos else 0,
        'categorias':      len(datos['categorias']),
        'metodos':         len(datos['metodos_pago']),
    }

# ── PDF ──────────────────────────────────────────

class GastAppPDF(FPDF):
    def header(self):
        self.set_fill_color(11,13,15)
        self.rect(0,0,210,16,'F')
        self.set_font('Helvetica','B',10)
        self.set_text_color(163,230,53)
        self.cell(0,16,'GastApp - Reporte Final de Analisis de Datos',align='C',
                  new_x=XPos.LMARGIN,new_y=YPos.NEXT)
        self.set_text_color(0,0,0)

    def footer(self):
        self.set_y(-11)
        self.set_font('Helvetica','I',7)
        self.set_text_color(150,150,150)
        self.cell(0,8,f'Pagina {self.page_no()} - Proyecto Integrador - Nuevas Tecnologias',align='C')

    def seccion(self, titulo):
        self.set_fill_color(163,230,53)
        self.set_text_color(11,13,15)
        self.set_font('Helvetica','B',10)
        self.cell(0,7,f'  {titulo}',fill=True,new_x=XPos.LMARGIN,new_y=YPos.NEXT)
        self.set_text_color(0,0,0)
        self.ln(2)

    def img_from_buf(self, buf, x=None, w=180, h=0):
        """Inserta imagen desde buffer en memoria, sin guardar en disco."""
        self.image(buf, x=x, w=w, h=h)

    def metricas_fila(self, items):
        ancho = 190/len(items)
        y0 = self.get_y()
        for label, valor in items:
            self.set_fill_color(245,245,245)
            self.set_draw_color(210,210,210)
            self.rect(self.get_x(), y0, ancho-2, 16, 'FD')
            self.set_font('Helvetica','B',12)
            self.set_text_color(30,30,30)
            self.set_xy(self.get_x(), y0+1)
            self.cell(ancho-2,6,str(valor),align='C',new_x=XPos.RIGHT,new_y=YPos.TOP)
            self.set_xy(self.get_x()-(ancho-2), y0+8)
            self.set_font('Helvetica','',7)
            self.set_text_color(100,100,100)
            self.cell(ancho-2,5,label.upper(),align='C',new_x=XPos.RIGHT,new_y=YPos.TOP)
            self.set_xy(self.get_x(), y0)
        self.set_xy(10, y0+18)


def generar_pdf(datos, metricas, bufs, nombre):
    ahora = datetime.now().strftime('%d/%m/%Y %H:%M:%S')
    pdf = GastAppPDF()
    pdf.set_auto_page_break(auto=True, margin=13)
    pdf.set_margins(10, 20, 10)

    # ── PÁG 1: Portada + métricas + volumen ──
    pdf.add_page()
    pdf.set_fill_color(240,240,240)
    pdf.rect(10,20,190,26,'F')
    pdf.set_font('Helvetica','B',20)
    pdf.set_text_color(20,20,20)
    pdf.set_xy(10,22)
    pdf.cell(190,9,'GastApp',align='C',new_x=XPos.LMARGIN,new_y=YPos.NEXT)
    pdf.set_font('Helvetica','',10)
    pdf.set_text_color(80,80,80)
    pdf.set_x(10)
    pdf.cell(190,7,'Reporte Final de Analisis de Datos - Proyecto Integrador',align='C',
             new_x=XPos.LMARGIN,new_y=YPos.NEXT)
    pdf.set_font('Helvetica','I',8)
    pdf.set_text_color(130,130,130)
    pdf.set_x(10)
    pdf.cell(190,5,f'Generado el {ahora}  |  API: {BASE_URL}',align='C',
             new_x=XPos.LMARGIN,new_y=YPos.NEXT)
    pdf.ln(3)

    pdf.seccion('Metricas Globales del Sistema')
    pdf.metricas_fila([
        ('Total Usuarios',   metricas['total_usuarios']),
        ('Activos',          metricas['usr_activos']),
        ('Bloqueados',       metricas['usr_bloqueados']),
        ('Total Gastos',     metricas['total_gastos']),
        ('Categorias',       metricas['categorias']),
    ])
    pdf.metricas_fila([
        ('Volumen Total',    fmt_moneda(metricas['total_gasto'])),
        ('Gasto Este Mes',   fmt_moneda(metricas['gasto_mes'])),
        ('Promedio/Gasto',   fmt_moneda(metricas['prom_gasto'])),
        ('Metodos de Pago',  metricas['metodos']),
    ])
    pdf.ln(1)

    pdf.seccion('1. Volumen de Gastos por Mes')
    pdf.img_from_buf(bufs['volumen'], w=183)

    pdf.seccion('2. Tendencia Acumulada de Gastos')
    pdf.img_from_buf(bufs['tendencia'], w=183)

    # ── PÁG 2: Canal + Top usuarios ──
    pdf.add_page()
    pdf.seccion('3. Monto Total por Canal de Compra')
    pdf.img_from_buf(bufs['canal'], w=183)

    pdf.seccion('4. Top 5 Usuarios con Mayor Gasto')
    pdf.img_from_buf(bufs['top_usuarios'], w=183)

    # ── PÁG 3: Pies lado a lado + Tabla ──
    pdf.add_page()
    pdf.seccion('5. Estado de Gastos y Estado de Usuarios')
    y0 = pdf.get_y()
    bufs['estado_gastos'].seek(0)
    bufs['estado_usuarios'].seek(0)
    pdf.image(bufs['estado_gastos'],  x=12,  y=y0, w=85)
    pdf.image(bufs['estado_usuarios'], x=113, y=y0, w=85)
    pdf.set_y(y0 + 85)
    pdf.set_font('Helvetica','I',7)
    pdf.set_text_color(130,130,130)
    pdf.cell(0,4,'Izquierda: estado de gastos  |  Derecha: estado de cuentas',
             new_x=XPos.LMARGIN,new_y=YPos.NEXT)
    pdf.set_text_color(0,0,0)
    pdf.ln(3)

    pdf.seccion('6. Top 5 Gastos Mas Altos')
    top5 = sorted(datos['gastos'], key=lambda g: g.get('valor',0), reverse=True)[:5]
    anchos = [12,72,32,38,30]
    headers = ['#','Descripcion','Fecha','Valor','Estado']
    pdf.set_fill_color(11,13,15)
    pdf.set_text_color(163,230,53)
    pdf.set_font('Helvetica','B',8)
    for h,w in zip(headers,anchos):
        pdf.cell(w,7,h,border=1,fill=True,align='C')
    pdf.ln()
    pdf.set_text_color(30,30,30)
    pdf.set_font('Helvetica','',8)
    for i,g in enumerate(top5):
        pdf.set_fill_color(248,248,248) if i%2==0 else pdf.set_fill_color(255,255,255)
        pdf.cell(anchos[0],6,f'#{i+1}',                            border=1,fill=True,align='C')
        pdf.cell(anchos[1],6,str(g.get('descripcion',''))[:38],     border=1,fill=True)
        pdf.cell(anchos[2],6,str(g.get('fecha','')),                border=1,fill=True,align='C')
        pdf.cell(anchos[3],6,fmt_moneda(g.get('valor',0)),          border=1,fill=True,align='R')
        pdf.cell(anchos[4],6,str(g.get('estadoGasto','')),          border=1,fill=True,align='C')
        pdf.ln()

    pdf.output(nombre)
    print(f"  v PDF generado: {nombre}")

# ── HTML ─────────────────────────────────────────

def generar_html(datos, metricas, b64):
    ahora = datetime.now().strftime('%d/%m/%Y %H:%M:%S')

    def mc(titulo, valor, color=ACCENT, icono=''):
        return f'<div class="metric-card"><div class="mi">{icono}</div><div class="mv" style="color:{color}">{valor}</div><div class="ml">{titulo}</div></div>'

    def gc(titulo, img, desc=''):
        return f'<div class="chart-card"><h3 class="ct">{titulo}</h3>{"<p class=cd>"+desc+"</p>" if desc else ""}<img src="data:image/png;base64,{img}" class="ci"/></div>'

    top5 = sorted(datos['gastos'], key=lambda g: g.get('valor',0), reverse=True)[:5]
    filas = ''.join(
        f"<tr><td>#{i+1}</td><td>{g.get('icono','💰')} {g.get('descripcion','')}</td>"
        f"<td>{g.get('fecha','')}</td><td class=mo>${g.get('valor',0):,.0f}</td>"
        f"<td><span class=ba>{g.get('estadoGasto','')}</span></td></tr>"
        for i,g in enumerate(top5)
    )

    return f"""<!DOCTYPE html><html lang="es"><head><meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<title>GastApp — Reporte</title>
<style>
@import url('https://fonts.googleapis.com/css2?family=Syne:wght@700;800&family=DM+Sans:wght@400;500;600&display=swap');
*{{box-sizing:border-box;margin:0;padding:0}}
:root{{--bg:#0b0d0f;--card:#1c2028;--bo:rgba(255,255,255,0.07);--ac:#a3e635;--tx:#f0f4f8;--mu:#8b95a1}}
body{{background:var(--bg);color:var(--tx);font-family:'DM Sans',sans-serif;padding:28px 20px}}
.wrap{{max-width:1100px;margin:0 auto}}
.hdr{{text-align:center;padding:36px 28px;background:var(--card);border-radius:18px;border:1px solid var(--bo);margin-bottom:32px}}
.hdr h1{{font-family:'Syne',sans-serif;font-size:2.2rem;font-weight:800;color:var(--ac);margin-bottom:6px}}
.hdr p,.hdr .fe{{color:var(--mu);font-size:.9rem}}
.st{{font-family:'Syne',sans-serif;font-size:1.1rem;font-weight:700;color:var(--ac);margin:28px 0 14px;padding-bottom:6px;border-bottom:1px solid var(--bo)}}
.mg{{display:grid;grid-template-columns:repeat(auto-fit,minmax(150px,1fr));gap:12px;margin-bottom:22px}}
.metric-card{{background:var(--card);border:1px solid var(--bo);border-radius:12px;padding:18px;text-align:center}}
.mi{{font-size:1.5rem;margin-bottom:5px}}.mv{{font-family:'Syne',sans-serif;font-size:1.5rem;font-weight:800;margin-bottom:3px}}
.ml{{font-size:.72rem;color:var(--mu);text-transform:uppercase;letter-spacing:.05em}}
.cg{{display:grid;gap:16px}}.c2{{grid-template-columns:1.4fr 1fr}}.ce{{grid-template-columns:1fr 1fr}}
.chart-card{{background:var(--card);border:1px solid var(--bo);border-radius:14px;padding:20px}}
.ct{{font-family:'Syne',sans-serif;font-size:.95rem;font-weight:700;margin-bottom:5px}}
.cd{{font-size:.8rem;color:var(--mu);margin-bottom:12px}}.ci{{width:100%;height:auto;border-radius:6px}}
.tc{{background:var(--card);border:1px solid var(--bo);border-radius:14px;padding:20px;overflow-x:auto}}
table{{width:100%;border-collapse:collapse;font-size:.86rem}}
thead tr{{background:rgba(255,255,255,.04)}}
th{{padding:9px 12px;text-align:left;font-size:.7rem;color:var(--mu);text-transform:uppercase;border-bottom:1px solid var(--bo)}}
td{{padding:10px 12px;border-bottom:1px solid var(--bo)}}
.mo{{font-family:'Syne',sans-serif;font-weight:700;color:var(--ac)}}
.ba{{display:inline-block;padding:2px 9px;border-radius:20px;background:rgba(163,230,53,.12);color:var(--ac);font-size:.76rem;font-weight:600}}
.ft{{text-align:center;margin-top:40px;padding:20px;color:var(--mu);font-size:.8rem;border-top:1px solid var(--bo)}}
@media(max-width:768px){{.c2,.ce{{grid-template-columns:1fr}}}}
</style></head><body>
<div class="wrap">
<div class="hdr"><h1>📊 GastApp</h1><p>Reporte Final de Análisis de Datos — Proyecto Integrador</p><div class="fe">Generado el {ahora} · Datos en tiempo real desde la API</div></div>

<div class="st">📈 Métricas Globales</div>
<div class="mg">
{mc('Total Usuarios',metricas['total_usuarios'],ACCENT,'👥')}
{mc('Usuarios Activos',metricas['usr_activos'],'#22c55e','✅')}
{mc('Bloqueados',metricas['usr_bloqueados'],'#ef4444','🔒')}
{mc('Total Gastos',metricas['total_gastos'],ACCENT,'📋')}
{mc('Volumen Total',f"${metricas['total_gasto']:,.0f}",ACCENT,'💰')}
{mc('Gasto Este Mes',f"${metricas['gasto_mes']:,.0f}",'#38bdf8','📅')}
{mc('Promedio/Gasto',f"${metricas['prom_gasto']:,.0f}",'#fb923c','📊')}
{mc('Categorías',metricas['categorias'],'#a78bfa','🏷️')}
{mc('Métodos de Pago',metricas['metodos'],'#f472b6','💳')}
</div>

<div class="st">💸 Análisis de Gastos</div>
<div class="cg c2">{gc('Volumen de Gastos por Mes',b64['volumen'],'Suma total por mes calendario.')}{gc('Estado de Gastos',b64['estado_gastos'],'Distribución por estado.')}</div>
<div class="cg" style="margin-top:16px">{gc('Tendencia Acumulada de Gastos',b64['tendencia'],'Crecimiento acumulado desde el inicio.')}</div>

<div class="st">🛒 Canal y Usuarios</div>
<div class="cg ce">{gc('Canal de Compra',b64['canal'],'Volumen por canal utilizado.')}{gc('Top 5 Usuarios',b64['top_usuarios'],'Usuarios con mayor gasto acumulado.')}</div>

<div class="st">👥 Estado de Usuarios</div>
<div class="cg" style="max-width:480px">{gc('Estado de Cuentas',b64['estado_usuarios'],'Proporción de cuentas activas o bloqueadas.')}</div>

<div class="st">🏆 Top 5 Gastos Más Altos</div>
<div class="tc"><table>
<thead><tr><th>#</th><th>Descripción</th><th>Fecha</th><th>Valor</th><th>Estado</th></tr></thead>
<tbody>{filas if filas else '<tr><td colspan=5 style="text-align:center;color:var(--mu)">Sin datos</td></tr>'}</tbody>
</table></div>

<div class="ft">GastApp · Proyecto Integrador — Nuevas Tecnologías · Python + Matplotlib + fpdf2<br/>API: {BASE_URL} · {ahora}</div>
</div></body></html>"""

# ── MAIN ─────────────────────────────────────────

def main():
    print("\n GastApp — Generando reporte...")
    print("-" * 50)

    print("\n Conectando con la API...")
    datos    = cargar_datos()
    gastos   = datos['gastos']
    usuarios = datos['usuarios']
    metodos  = datos['metodos_pago']

    if not gastos and not usuarios:
        print("\n No hay datos. Verifica que Spring Boot este corriendo en http://localhost:8080")
        return

    print("\n Calculando metricas...")
    metricas = calcular_metricas(datos)

    print(" Generando graficas...")
    # Graficas HTML (base64)
    b64 = {
        'volumen':        g_volumen(gastos),
        'estado_gastos':  g_estado_gastos(gastos),
        'canal':          g_canal(gastos),
        'top_usuarios':   g_top_usuarios(gastos, usuarios),
        'estado_usuarios': g_estado_usuarios(usuarios),
        'tendencia':      g_tendencia(gastos),
    }
    # Buffers PDF (en memoria)
    bufs = {
        'volumen':        g_volumen(gastos, pdf=True),
        'estado_gastos':  g_estado_gastos(gastos, pdf=True),
        'canal':          g_canal(gastos, pdf=True),
        'top_usuarios':   g_top_usuarios(gastos, usuarios, pdf=True),
        'estado_usuarios': g_estado_usuarios(usuarios, pdf=True),
        'tendencia':      g_tendencia(gastos, pdf=True),
    }

    ts = datetime.now().strftime('%Y%m%d_%H%M%S')

    print(" Construyendo HTML...")
    nombre_html = f"reporte_gastapp_{ts}.html"
    with open(nombre_html, 'w', encoding='utf-8') as f:
        f.write(generar_html(datos, metricas, b64))
    print(f"  v HTML: {nombre_html}")

    print(" Construyendo PDF...")
    nombre_pdf = f"reporte_gastapp_{ts}.pdf"
    generar_pdf(datos, metricas, bufs, nombre_pdf)

    print(f"\n Reportes generados:")
    print(f"   HTML -> {nombre_html}")
    print(f"   PDF  -> {nombre_pdf}\n")


if __name__ == '__main__':
    main()
