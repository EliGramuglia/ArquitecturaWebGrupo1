TODO:
- Definir tarifa extra para reinicio por pausa extensa (CONSULTAR)

- Realizar un modelamiento de las distintas funcionalidades y datos que debe guardar el sistema. Este
  modelamiento debe ser capturado en términos de (sub-)dominios, apuntando a un diseño con
  microservicios.


MARI: DONE!
- Como usuario quiero saber cuánto he usado los monopatines en un período
- AHORA DEVUELVO ALGO ASI
{
  "idUsuario": 7,
  "cantidadViajes": 5,
  "cuentaCompartida": [1, 2] (devolver los usuarios que usaron los monopatines)
}

- , y opcionalmente si otros usuarios relacionados a mi cuenta los han usado.  (devolver los usuarios que usaron los monopatines)
YO SOY USUARIO 7 Y TENGO LA CUENTA 2....TODOS LOS QUE TENGAN CUENTA 2 DEBO CONTAR CUANTAS VECES 
USUARON LOS MONOPATINES EN ESE PERIODO? O CON UN BOOLEANO YA ALCANZA




-en updateCuenta deberia poder asignar otros usuarios a la misma cuenta

DONE:
-El usuario deberia mostrar que cuentas posee asignadas /GET
{
    "id": 2,
    "nombre": "Thresh",
    "apellido": "Cabrera",
    "email": "assdfsdfdfgl@example.com",
    "nroCelular": 123557890,
    "rol": "CLIENTE",
    "idCuentas": [
        1,
        2
    ]
}
- En una cuenta premium, el usuario paga un valor mensual fijo,
  y tienen recorridos ilimitados hasta 100 km por mes, y luego de eso paga el 50% de la tarifa,....
- La cuenta puede ser básica o premium.
- gateway
- Registrar monopatín en mantenimiento (debe marcarse como no disponible para su uso) -> CONSULTA
- Agregar monopatín
- Quitar monopatín
- Definir precio
- Anular cuenta
- Como administrador quiero poder anular cuentas de usuarios, para inhabilitar el uso
  momentáneo de la aplicación.
- Como usuario quiero buscar un listado de los monopatines cercanos a mi zona, para poder
  encontrar un monopatín cerca de mi ubicación
  EN USUARIO HACER UN ENDPOINT
- Como administrador quiero consultar el total facturado en un rango de meses de cierto año.

- Una vez validado el modelamiento, generar las entidades y relaciones correspondientes, y mapearlo
    a una base de datos SQL. En ciertos casos (por ej., recorridos), puede utilizarse una base NoSQL (por
    ej., MongoDB)
- Diseñar un backend básico de (micro-)servicios que permita realizar el ABM de las entidades (para
    así poblar y gestionar la(s) base(s) de datos) y dar soporte a las principales funcionalidades antes
    mencionadas. En este diseño, considerar que cada microservicio contará (preferentemente) con una
    base de datos separada.

MONOPATIN -> FEIGNCLIENT DE VIAJE
- Registrar parada
- Quitar parada
- CRUDO COMPLETO
- Ubicar monopatín en parada (opcional)

- f. Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
habilite los nuevos precios.

- Como administrador quiero consultar el total facturado en un rango de meses de cierto año.
- Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.
    (VIAJE)
- Generar reporte de uso de monopatines por kilómetros, por tiempo (con y sin pausas)
- Como administrador quiero poder generar un reporte de uso de monopatines por kilómetros
  para establecer si un monopatín requiere de mantenimiento. Este reporte debe poder
  configurarse para incluir (o no) los tiempos de pausa.




