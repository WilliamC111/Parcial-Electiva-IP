import subprocess
import shutil
from pathlib import Path
from datetime import datetime

# Rutas base
BASE_DIR = Path(__file__).resolve().parent  # backend/bebidas/tests
BEBIDAS_DIR = BASE_DIR.parent               # backend/bebidas
BACKEND_DIR = BEBIDAS_DIR.parent            # backend/
REPORTS_DIR = BACKEND_DIR / "reportes"
PYLINTRC_PATH = BACKEND_DIR / ".pylintrc"

# Crear carpeta de reportes si no existe
REPORTS_DIR.mkdir(exist_ok=True)

# Crear archivo .pylintrc si no existe
if not PYLINTRC_PATH.exists():
    PYLINTRC_PATH.write_text(
        "[MESSAGES CONTROL]\ndisable=C0114,C0115,C0116\n",
        encoding="utf-8"
    )

# Rutas de reportes
coverage_txt = REPORTS_DIR / "coverage.txt"

# Eliminar archivos previos
if coverage_txt.exists():
    coverage_txt.unlink()

# Comandos
comandos = {
    "pylint": ["pylint", str(BEBIDAS_DIR), f"--rcfile={PYLINTRC_PATH}"],
    "mypy": ["mypy", str(BEBIDAS_DIR)],
    "pytest-cov": [
        "pytest",
        "--cov=bebidas",
        "--cov-report=term-missing"
    ]
}

# Ejecutar herramientas
for nombre, comando in comandos.items():
    print(f"Ejecutando {nombre}...")
    if nombre == "pytest-cov":
        with open(coverage_txt, "w", encoding="utf-8") as f:
            subprocess.run(comando, stdout=f, stderr=subprocess.STDOUT, cwd=BEBIDAS_DIR)
    else:
        resultado = subprocess.run(comando, capture_output=True, text=True, cwd=BEBIDAS_DIR)
        archivo = REPORTS_DIR / f"{nombre}_report.txt"
        archivo.write_text(resultado.stdout + "\n" + resultado.stderr, encoding="utf-8")

# Procesar resultados
pylint_output = (REPORTS_DIR / "pylint_report.txt").read_text(encoding="utf-8") if (REPORTS_DIR / "pylint_report.txt").exists() else ""
warnings = pylint_output.lower().count("warning")
errors = pylint_output.lower().count("error")

mypy_output = (REPORTS_DIR / "mypy_report.txt").read_text(encoding="utf-8") if (REPORTS_DIR / "mypy_report.txt").exists() else ""
mypy_errors = mypy_output.lower().count("error:")

if coverage_txt.exists():
    try:
        coverage_output = coverage_txt.read_text(encoding="utf-8")
    except UnicodeDecodeError:
        coverage_output = coverage_txt.read_text(encoding="latin-1")
else:
    coverage_output = "Sin resultados"

coverage_percentage = "No disponible"
for line in coverage_output.splitlines():
    if line.strip().startswith("TOTAL"):
        parts = line.split()
        if parts and parts[-1].endswith("%"):
            coverage_percentage = parts[-1]

# Crear Markdown
timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

contenido_md = [
    "# Análisis Estático y Cobertura - VirtualCoffee (API Bebidas)",
    "",
    f"Fecha de análisis: {timestamp}",
    "",
    "---",
    "",
    "## Herramientas utilizadas",
    "",
    "- pylint (con archivo .pylintrc aplicado)",
    "- mypy",
    "- pytest-cov",
    "",
    "---",
    "",
    "## Resultados del análisis",
    "",
    f"- Warnings detectados (pylint): {warnings}",
    f"- Errores detectados (pylint): {errors}",
    f"- Errores de tipos (mypy): {mypy_errors}",
    f"- Cobertura de pruebas (pytest-cov): {coverage_percentage}",
    "",
    "---",
    "",
    "## Reglas desactivadas temporalmente",
    "",
    "- C0114: Falta de docstring en módulos",
    "- C0115: Falta de docstring en clases",
    "- C0116: Falta de docstring en funciones/métodos",
    "",
    "Motivo: Desactivadas para enfocarse primero en la funcionalidad principal del backend antes de la documentación formal.",
    "",
    "---",
    "",
    "## Observaciones",
    "",
    "- Se eliminaron imports no utilizados.",
    "- Se mejoraron nombres de variables conforme a PEP8.",
    "- Se modularizó el código en carpetas adecuadas (tests, persistencia, excepciones).",
    ""
]

analisis_md = BACKEND_DIR / "ANALISIS_ESTATICO.md"
analisis_md.write_text("\n".join(contenido_md), encoding="utf-8")

print("\nAnálisis completado correctamente.")
print(f"Reportes en: {REPORTS_DIR}")
print(f"Resumen en: {analisis_md}")
