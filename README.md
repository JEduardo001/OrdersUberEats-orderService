Order Service

Microservicio encargado de la gestión del ciclo de vida de los pedidos, incluyendo la creación, actualización de estados y coordinación asíncrona mediante el patrón Outbox.




Stack Tecnológico
Java: 21

Framework: Spring Boot 4.0.1

Base de Datos: PostgreSQL

Mensajería: Apache Kafka

Service Discovery: Netflix Eureka




API Endpoints

Base Path: /api/v1/order

GET /: Obtiene lista paginada de pedidos (parámetros page y size).

GET /{idOrder}: Obtiene el detalle de un pedido específico por su UUID.

PUT /: Actualiza la información técnica o de negocio de un pedido existente.



Comunicación Orientada a Eventos


El servicio utiliza Kafka para la orquestación de procesos de negocio y la gestión de inventario.

Eventos Consumidos
order.requested: Inicia la lógica de creación del pedido y registra el evento en la tabla Outbox.

inventory.stock.reserved: Transiciona el estado del pedido a PENDING_TO_DO tras la confirmación de existencias.

inventory.stock.reserved.failed: Deshabilita el pedido (estado DISABLE) si el inventario no pudo ser reservado.




Eventos Producidos

order.created.pending: Notifica la persistencia inicial del pedido para validaciones posteriores.

changed.status.order.failed: Informa fallos en la actualización de estados críticos.

failed.send.event.dlq: Canal de Dead Letter Queue para la gestión de errores en la mensajería.



Patrones y Lógica de Negocio

Transactional Outbox: Asegura consistencia atómica entre la persistencia en PostgreSQL y la publicación en Kafka.

Idempotencia: Control de duplicados mediante ProcessedEventService, verificando el ID del evento antes de procesar.

Trazabilidad Distribuida: Uso de correlationId en las cabeceras de los registros de Kafka para seguimiento entre microservicios.




Configuración de Infraestructura

Puerto de servicio: 5012

Base de Datos: OrderServiceOrderUberEatsDB (PostgreSQL)

Service Discovery: Cliente activo registrado en Eureka Server.




