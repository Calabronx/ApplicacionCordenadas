# AplicacionCordenadas
>API REST que consume una API publica de cordenadas de provincias https://apis.datos.gob.ar/georef/api/provincias, para de devolver latitud y longitud como principal funcion.

[Mas informacion de la API Publica](https://datosgobar.github.io/georef-ar-api/)

# Las tecnologias que utiliza:

- Spring Framework
- Java 11
- Hibernate
- MySQL para bases de datos
- SpringFox y Swagger 2 para documentar API REST.
- SL4J framework y Logback para manejo de Logging por archivo *logback.xml*.
- Spring Security Core para el uso de Authenticacion para un usuario unico, con BycriptEncoder para vincular la contraseña con la que esta encriptada en la base.
- JUnit5 y Mockito para testear el service.
- HTML, Bootstrap 5 y CSS para una simple vista del frontend.
- JPA Repository

# Acceder a la App
>Al ejecutar la applicacion y acceder al http://localhost:8080/ lo redigira a un Home, al darle click *aqui* la API le pedira que se logee con los siguientes usuario y contraseña en el siguiente formulario de Login.

>usuario: francis
>password: thepassword
![image](https://user-images.githubusercontent.com/69681105/174823954-8249f2d1-ca80-4b11-90d9-2c80109b8e71.png)
>Si los parametros de usuario son mal ingresados, no lo dejara ingresar y le devolvera lo siguiente
![image](https://user-images.githubusercontent.com/69681105/174816034-779a2894-4fe6-4f95-b503-204d5be106ab.png)

>Si intenta ingresar al index principal, o a swagger, o otro endpoint, lo redigira automaticamente al Login, esto por supuesto es manejado por Spring Security core.
>El usuario y contraseña son comparados con un usuario unico e existente creado en la base, que tiene la contraseña encriptada.
La clase *WebSecurityConfig.java* del paquete security tiene configurado la principal funcion de redireccion de endpoints y seguridad.
![image](https://user-images.githubusercontent.com/69681105/174817148-9adae315-2e73-4d1b-86c1-7e0d66aa42e8.png)

>Index principal de la applicacion al logearse con exito
![image](https://user-images.githubusercontent.com/69681105/174819686-466b47e9-1f40-4057-9f32-80d5bea78885.png)
Lamentablemente no pude lograr que funcione el Buscar provincia por nombre desde el formulario html, tiene que hacerse a mano pasandole el nombre de la provincia por el endpoint.

En Show History se puede ver un listado de todas las provincias que busco , con sus lat y long. Esto la applicacion le hace un seguimiento de lo que el usuario ingresa y lo guarda en la base. Sin permitir duplicados.


# Request importantes de las API

> Consumo de API Publica Provincias
* GET localhost:8080/api/v1/cordinates_data/obtain_geo_cords/{nombre_provincia}

Devuelve la latitud y longitud de la provincia buscada por nombre. Obtenido al consumir la API Publica.
>Ej: pasandole /api/v1/cordinates_data/obtain_geo_cords/Santiago devolvera latitud y longitud de Santiago del Estero.
![image](https://user-images.githubusercontent.com/69681105/174812268-ce477b03-e782-46e5-bec0-ec5a763e500c.png)
Esto te realiza con el metodo public ResponseEntity<String> obtainProvinceCordsWithName(String name), devuelve una respuesta con las
cordenadas de la provincia.

# SpringFox y Swagger 2 Documentation API
>Para acceder a la UI de Swagger 2 desde este link http://localhost:8080/swagger-ui.html#/home-controller 
>Los endpoints importantes de la app
![image](https://user-images.githubusercontent.com/69681105/174826488-dc9f1bc6-0786-4e47-b99d-edce4f58c723.png)
![image](https://user-images.githubusercontent.com/69681105/174826789-cbc8e807-c3e5-4884-9692-4bb188884eda.png)
![image](https://user-images.githubusercontent.com/69681105/174827054-7a92e780-0cec-446c-8972-c59614db2194.png)


* GET localhost:8080/index_history

>Muestra el historial de cordenadas de provincias buscadas

# SL4J y Logback Systema de Logging

>La API utiliza slf4j-api contra logback-core y logback-classic

>Lo hace muy facil de configurar en un archivo logback.xml
La aplicacion logea en la carpeta logs, un archivo CordinatesLog.text que es cargado hasta un maximo de 10MB y guardado con fecha al llegar a su limite.
Luego de eso crea otro archivo
![image](https://user-images.githubusercontent.com/69681105/174822988-a9e383b8-8072-4955-9135-de25114dc523.png)

# Archivo de configuracion de conecciones

>Spring utiliza el archivo *application.properties* para configurar los parametros de coneccion y configuraciones de las librerias que utiliza la api misma.

# Hibernate

>Con el uso del ORM Hibernate, al ejecutar por primera vez la applicacion, creara las tablas automaticamente para manejar y almacenar sus datos, con su mapeo relacional de objectos automatico.
>En el application.properties esta seteado para logs de las queries con jpa y mapeo de entities.

![image](https://user-images.githubusercontent.com/69681105/174865706-f6a47a5b-eafc-4d0a-ac30-e8595f0754a9.png)
  
  













