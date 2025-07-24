# Proyecto Full Stack: React + Spring Boot

Este repositorio contiene un proyecto dividido en dos partes:

- `frontend/`: aplicación desarrollada en **React** con **Bootstrap** y **React Router**.
- `backend/`: servicio desarrollado en **Java 17** usando **Spring Boot 3.5.3**.

---

## 📁 Estructura del repositorio

```
.
├── frontend/     # Aplicación React (Create React App)
└── backend/      # Aplicación Spring Boot (Java 17)
```

---

## 🚀 Requisitos previos

### 🔹 Para correr el backend:

- Java 17+
- (Opcional) Maven 3.9.x (`brew install maven`)
- O usa el wrapper: `./mvnw`

### 🔹 Para correr el frontend:

- Node.js 18+ y npm (`brew install node`)
- (Opcional) Yarn si prefieres (`npm install -g yarn`)

---

## ▶️ Cómo ejecutar el proyecto

### 🔧 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/tu-repo.git
cd tu-repo
```

---

### 🔹 2. Ejecutar el backend (Spring Boot)

```bash
cd backend
# Opción 1: usando Maven global
mvn spring-boot:run

# Opción 2: usando el wrapper incluido
chmod +x mvnw
./mvnw spring-boot:run
```

📍 El backend estará disponible en `http://localhost:8080/`

---

### 🔹 3. Ejecutar el frontend (React)

```bash
cd frontend
npm install
npm start
```

📍 El frontend estará en `http://localhost:3000/`

---

## 📡 API de prueba

El backend tiene un endpoint básico para verificar funcionamiento:

```
GET http://localhost:8080/
→ "¡Hola desde Spring Boot!"
```

---

## 🧠 Notas

- El backend permite CORS por defecto si es necesario conectarlo con el frontend.
- El proyecto está pensado para correr en local con puertos separados (`8080` y `3000`).
- Puedes extender el backend con controladores REST y base de datos (JPA, H2, MySQL, etc.).

---

## 📦 Tecnologías usadas

### Backend:
- Java 17
- Spring Boot 3.5.3
- Spring Web
- Spring DevTools

### Frontend:
- React (Create React App)
- React Router DOM
- Bootstrap 5