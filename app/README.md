Huerto Hogar

## Integrantes
Paula Ulloa
William Castillo
Cesar Crespo

## Funcionalidades Principales
1.  Catálogo de Productos: Visualización de frutas, verduras y otros
2.  Carrito de Compras: Lógica completa de agregar, eliminar y calcular totales (CRUD Local)
3.  Gestión de Usuario: Registro y Login persistente utilizando DataStore
4.  Sección de Tips: API Externa para mostrar consejos.
5.  Formulario: Gestión de sesión y datos de usuario.

## Endpoints Utilizados

### 1. API Externa (Tips)
Se utiliza la API pública de JSONPlaceholder para simular un feed de consejos de jardinería.
URL: https://jsonplaceholder.typicode.com/
Endpoint: GET /posts

### 2. DataStore

Auth: UserDataStore (Simula /login y /register)
Productos: HomeRepository (Simula GET /products)

### Pasos para Ejecutar
1.  Clonar el repositorio.
2.  Abrir en Android Studio y sincronizar Gradle.
3.  Para generar el APK, la configuración de firma es automática desde build.gradle.kts


## Evidencia en carpeta