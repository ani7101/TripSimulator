# Naming convention

We use the `java.util.UUID.randomUUID` to generate a 16 character string which has very small probability of collision

This is stored as an Arraylist, named uniqueIds in the generator functions and is stored in different data fields in the following convention

## Vehicle & Trips

- **Vehicle** - simulation-vehicle-{uniqueId}
- **Device name** - simulation-obd2-device-{uniqueId}
- **Device Identifiers (Hardware IDs)** - simulation-obd2-sensor-{uniqueId}
- **Trip** - simulation-trip-{uniqueIds}


## Equipment

- **Equipment** - simulator-equipment-sensor-{uniqueId}
- **Ship unit** - simulator-ship-unit-sensor-{uniqueId}
- **Ship item** - simulator-ship-item-sensor-{uniqueId}