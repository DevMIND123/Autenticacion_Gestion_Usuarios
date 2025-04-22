# Manual del Backend

Este documento describe el proceso para compilar, generar y ejecutar el artefacto del backend de la plataforma.

## Requisitos previos

- Tener instalado **Java** (versión requerida por el proyecto)
- Tener instalado **Maven**
- Clonar este repositorio
- Asegurarse de tener el archivo **.env** en el directorio raíz del proyecto, el cual contiene las variables de entorno necesarias para la conexión a la base de datos y configuraciones relacionadas con JWT.

## Generación del artefacto

Una vez clonado el repositorio, se deben seguir los siguientes pasos para generar el artefacto:

1. **Limpiar versiones anteriores del proyecto:**

```bash
mvn clean
```

2. **Instalar las dependencias necesarias del proyecto:**

```bash
mvn install
```

3. **Empaquetar el proyecto para generar el artefacto:**

```bash
mvn package
```

También es posible ejecutar directamente:

```bash
mvn clean install package
```

Este comando limpia versiones anteriores, instala las dependencias y empaqueta el proyecto generando el artefacto.

## Ubicación del artefacto

El artefacto generado es un archivo `.jar` con sufijo `-SNAPSHOT.jar`. Este se encuentra dentro de la carpeta `target`.

Ejemplo:
```
target/nombre-del-proyecto-0.0.1-SNAPSHOT.jar
```

## Ejecución del artefacto

Para ejecutar el artefacto y dejar la aplicación escuchando, se utiliza el `Dockerfile` incluido en el proyecto. En este archivo ya se encuentra el comando que inicializa el `.jar` generado por Maven.

Al construir y ejecutar el contenedor Docker, la plataforma quedará activa y en escucha.

## Configuración de CORS

Para permitir la comunicación entre el frontend, la máquina de pruebas y el backend, se realizó la configuración de **CORS** en el entorno. Esto se hizo modificando la clase `SecurityConfig.java`, donde se incluyeron los orígenes correspondientes para habilitar el acceso desde las plataformas autorizadas.

## Uso de Docker Compose

Este proyecto incluye un archivo `docker-compose.yml` pensado para el **entorno de desarrollo**.  
Si se desea realizar un despliegue local que requiera una base de datos, es necesario sustituir este archivo por la versión manejada por el equipo de desarrollo.

Adicionalmente, se debe ajustar el archivo `application.properties` para que apunte a la base de datos local correspondiente.

