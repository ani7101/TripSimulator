package shipItemsAndEquipments;

import shipItemsAndEquipments.subclasses.AttributeValue;
import shipItemsAndEquipments.subclasses.Tracker;
import utils.Generator;

import java.util.ArrayList;
import java.util.List;

import static shipItemsAndEquipments.EquipmentDeviceGenerator.createEquipmentDevice;

public class EquipmentGenerator {

    //region Equipments

    public static Equipment randomizedEquipment(
            String equipmentConnectorUrl,
            String username,
            String password,
            int pickupStopSequence,
            int dropStopSequence,
            int requiredShipUnits,
            int requiredShipItems
    ) {
        Equipment equipment = new Equipment();

        String uniqueId = Generator.generateRandomUUID();
        String tripEquipmentRef = generateTripEquipmentRef(uniqueId);

        equipment.setEquipmentNumber(generateEquipmentNumber(uniqueId));
        equipment.setTripEquipmentRef(tripEquipmentRef);

        // Initializes the lists for the ship units
        ArrayList<String> shipUnitUniqueIds = Generator.generateRandomUUID(requiredShipItems);

        ArrayList<ShipUnit> shipUnits = new ArrayList<>();
        double maxWeight = 0.0;
        double maxVolume = 0.0;

        for (int i = 0; i < requiredShipUnits; i++) {
            ShipUnit shipUnit = randomizedShipUnit(equipmentConnectorUrl, username, password, shipUnitUniqueIds.get(i), tripEquipmentRef, pickupStopSequence, dropStopSequence, requiredShipItems);

            maxWeight += shipUnit.getWeight().getValue();
            maxVolume += shipUnit.getVolume().getValue();

            shipUnits.add(shipUnit);
        }

        equipment.setPickupStopSequence(pickupStopSequence);
        equipment.setDropStopSequence(dropStopSequence);

        equipment.setMaxWeight(new AttributeValue(maxWeight + 5, "kg"));
        equipment.setMaxVolume(new AttributeValue(maxVolume + 5, "m3"));

        equipment.setTrackers(new ArrayList<>(List.of(
                new Tracker(createEquipmentDevice(equipmentConnectorUrl, username, password, "equipment", uniqueId))
        )));
        equipment.setShipUnits(shipUnits);

        return equipment;
    }

    //endregion

    //region Ship units

    public static ShipUnit randomizedShipUnit(
            String equipmentConnectorUrl,
            String username,
            String password,
            String uniqueId,
            String tripEquipmentRef,
            int pickupStopSequence,
            int dropStopSequence,
            int requiredShipItems
    ) {
        ShipUnit shipUnit = new ShipUnit();

        String shipUnitNumber = generateShipUnitNumber(uniqueId);

        shipUnit.setShipUnitNumber(shipUnitNumber);
        shipUnit.setTripEquipmentRef(tripEquipmentRef);
        shipUnit.setShipUnitKey(generateShipUnitKey(uniqueId));
        shipUnit.setShipUnitType(generateShipUnitType(uniqueId));

        // Creating a list of random Strings
        ArrayList<String> shipItemUniqueIds = Generator.generateRandomUUID(requiredShipItems);

        // Initialization for lists
        ArrayList<String> shipOrders = new ArrayList<>(requiredShipItems);
        ArrayList<ShipItem> shipItems = new ArrayList<>(requiredShipItems);
        ArrayList<String> commodities = new ArrayList<>(requiredShipItems);

        double weight = 0.0;
        double volume = 0.0;

        for (int i = 0; i < requiredShipItems; i++) {
            ShipItem shipItem = randomizedShipItem(equipmentConnectorUrl, username, password, shipItemUniqueIds.get(i), shipUnitNumber, tripEquipmentRef, pickupStopSequence, dropStopSequence, i + 1);

            shipItems.add(shipItem);
            shipOrders.add(shipItem.getShipOrderNumber());
            commodities.add("commodity-" + (i + 1));

            weight += shipItem.getWeight().getValue();
            volume += shipItem.getVolume().getValue();
        }

        shipUnit.setShipOrderNumbers(shipOrders);
        shipUnit.setCommodities(commodities);

        shipUnit.setWeight(new AttributeValue(weight, "kg"));
        shipUnit.setVolume(new AttributeValue(volume, "m3"));

        shipUnit.setPickupStopSequence(pickupStopSequence);
        shipUnit.setDropStopSequence(dropStopSequence);

        shipUnit.setTrackers(new ArrayList<>(
                List.of(new Tracker(createEquipmentDevice(equipmentConnectorUrl, username, password, "ship-unit", uniqueId)))
        ));

        shipUnit.setShipItems(shipItems);

        return shipUnit;
    }


    //endregion
    //region Ship items

    public static ShipItem randomizedShipItem(
            String equipmentConnectorUrl,
            String username,
            String password,
            String uniqueId,
            String shipUnitNumber,
            String tripEquipmentRef,
            int pickupStopSequence,
            int dropStopSequence,
            int commodityNumber
    ) {
        ShipItem shipItem = new ShipItem();

        ShipOrder shipOrder = new ShipOrder(uniqueId);
        shipItem.setShipOrder(shipOrder);
        shipItem.setShipOrderNumber(shipOrder.getOrderNumber());

        shipItem.setShipUnitNumber(shipUnitNumber);
        shipItem.setTripEquipmentRef(tripEquipmentRef);

        shipItem.setShipItemKey(generateShipItemKey(uniqueId));
        shipItem.setShipItemNumber(generateShipItemNumber(uniqueId));

        shipItem.setCommodity("commodity-" + commodityNumber);

        shipItem.setWeight(new AttributeValue(Generator.generateRandomNumber(10, 20), "kg"));

        shipItem.setQuantity((int) Generator.generateRandomNumber(10, 50));

        shipItem.setVolume(new AttributeValue(Generator.generateRandomNumber(3, 10), "m3"));

        shipItem.setPickupStopSequence(pickupStopSequence);
        shipItem.setDropStopSequence(dropStopSequence);

        shipItem.setTrackers(new ArrayList<>(
                List.of(new Tracker(createEquipmentDevice(equipmentConnectorUrl, username, password, "ship-item", uniqueId)))
        ));

        return shipItem;
    }

    //endregion
    //region Utils

    private static String generateEquipmentNumber(String uniqueId) {
        return "EQUIPMENT-NUMBER-" + uniqueId;
    }

    private static String generateTripEquipmentRef(String uniqueId) { return "EQUIPMENT-REF-" + uniqueId; }

    private static String generateShipUnitNumber(String uniqueId) {
        return "SHIP-UNIT-" + uniqueId;
    }

    private static String generateShipUnitKey(String uniqueId) {
        return "SHIP-UNIT-KEY-" + uniqueId;
    }

    private static String generateShipUnitType(String uniqueId) {
        return "SHIP-UNIT-TYPE-" + uniqueId;
    }

    private static String generateShipItemKey(String uniqueId) {
        return "SHIP-ITEM-KEY-" + uniqueId;
    }

    private static String generateShipItemNumber(String uniqueId) {
        return "SHIP-ITEM-NUMBER-" + uniqueId;
    }

    //endregion

}
