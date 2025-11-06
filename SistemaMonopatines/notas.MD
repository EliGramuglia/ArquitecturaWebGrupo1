TODO:
- Definir tarifa extra para reinicio por pausa extensa
- Generar reporte de uso de monopatines por kilómetros, por tiempo (con y sin pausas)

- Realizar un modelamiento de las distintas funcionalidades y datos que debe guardar el sistema. Este
  modelamiento debe ser capturado en términos de (sub-)dominios, apuntando a un diseño con
  microservicios.

- Como administrador quiero poder generar un reporte de uso de monopatines por kilómetros
  para establecer si un monopatín requiere de mantenimiento. Este reporte debe poder
  configurarse para incluir (o no) los tiempos de pausa.

- Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.
  (VIAJE)

- Como administrador quiero ver los usuarios que más utilizan los monopatines, filtrado por
  período y por tipo de usuario. (en VIAJE)
MAX idUsuario between mes1 a mes2 GROUP BY premium - comunes

- Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
  habilite los nuevos precios. CONSULTAR!!!!! ES UN UPDATE DEL CAMPO TARIFA EN VIAJE ?? CON EL SETTER

- Como usuario quiero saber cuánto he usado los monopatines en un período, y opcionalmente si
  otros usuarios relacionados a mi cuenta los han usado.
  

ELI:
arreglo el micro viaje
- Como administrador quiero consultar el total facturado en un rango de meses de cierto año.
  /viajes/total-facturado -> count getTarifa() between mes1 a mes2 AND año=año

MARI:
- Como usuario quiero buscar un listado de los monopatines cercanos a mi zona, para poder
  encontrar un monopatín cerca de mi ubicación
  EN USUARIO HACER UN ENDPOINT

FRAN:






DONE:

- Registrar monopatín en mantenimiento (debe marcarse como no disponible para su uso) -> CONSULTA
- Agregar monopatín
- Quitar monopatín
- Definir precio
- Anular cuenta
- Como administrador quiero poder anular cuentas de usuarios, para inhabilitar el uso
  momentáneo de la aplicación.

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

DUDAS: 
- Anular cuenta
- Como administrador quiero poder anular cuentas de usuarios, para inhabilitar el uso
  momentáneo de la aplicación.

El microservicio Usuario (Luego con la validación, Admin) tendría el endpoint, o está bien
por ej iniciar desde CuentaController?

La tarifa puede quedar sin fecha de fin de vigencia ?
Si tiene que tener fecha ed vigencia -> Agregar en la Entidad Tarifa (cuando se crea la tabla) Not NUll
Si puede crearse sin fecha de fin de vigencia -> Agregar en el metodo que crea tarifas esto
ultimaTarifa.setFechaFinVigencia(nuevaTarifa.getFechaInicioVigencia().minusDays(1));
tarifaRepository.save(ultimaTarifa);
para que le settee la fecha de ayer y entonces finalice la tarifa y entre en vigencia la actual.


- Como administrador quiero consultar el total facturado en un rango de meses de cierto año.



