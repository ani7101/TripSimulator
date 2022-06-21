# Naming convention

We use the `java.util.UUID.randomUUID` to generate a 16 character string which has very small probability of collision

This is stored as an Arraylist, named uniqueIds in the generator functions and is stored in different data fields in the following convention

- **Vehicle** - simulation-vehicle-{uniqueIds}
- **Device name** - simulation-obd2-device-{uniqueId}
- **Device Identifiers (Hardware IDs)** - simulation-obd2-sensor-{uniqueId}
- **Trip** - simulation-trip-{uniqueIds}