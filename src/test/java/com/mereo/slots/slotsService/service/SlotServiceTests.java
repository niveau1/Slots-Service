package com.mereo.slots.slotsService.service;

import com.mereo.slots.slotsService.entity.ServiceTime;
import com.mereo.slots.slotsService.entity.Slot;
import com.mereo.slots.slotsService.service.SlotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SlotServiceTests {
    @Autowired
    private SlotService slotService;

    @Test
    public void testMethodeReversList(){
        Slot slot1 = new Slot();
        LocalDate newDateService = LocalDate.of(2024, 1, 20);
        LocalTime newBeginService = LocalTime.of(9, 0);
        LocalTime newEndService = LocalTime.of(18, 0);
        // Utiliser les setters
        slot1.setDateService(newDateService);
        slot1.setHeureService(new ServiceTime(newBeginService, newEndService));

        Slot slot2 = new Slot(LocalDate.of(2024, 1, 21), new ServiceTime(LocalTime.of(8, 0), LocalTime.of(10, 0)));
        Slot slot3 = new Slot(LocalDate.of(2024, 1, 20), new ServiceTime(LocalTime.of(8, 0), LocalTime.of(9, 0)));

        // Ajout des objets A à une liste
        List<Slot> liste = new ArrayList<>();
        liste.add(slot1);
        liste.add(slot2);
        liste.add(slot3);

        liste = slotService.reversList(liste);
        assertEquals(slot3, liste.get(0));
        assertEquals(slot2, liste.get(2));

        // Affichage des éléments triés
        for (Slot a : liste) {
            System.out.println("Date de service : " + a.getDateService() + ", Heure de début : " + a.getHeureService().getBeginService() +  ", Heure de fin : " + a.getHeureService().getEndService() );
        }
    }

    @Test
    void testConfusionList() {
        Slot slot1 = new Slot(LocalDate.of(2024, 1, 20), new ServiceTime(LocalTime.of(8, 0), LocalTime.of(10, 0)));
        Slot slot2 = new Slot(LocalDate.of(2024, 1, 23), new ServiceTime(LocalTime.of(8, 0), LocalTime.of(12, 0)));
        Slot slot3 = new Slot(LocalDate.of(2024, 1, 20), new ServiceTime(LocalTime.of(14, 0), LocalTime.of(15, 0)));
        Slot slot4 = new Slot(LocalDate.of(2024, 1, 22), new ServiceTime(LocalTime.of(15, 0), LocalTime.of(18, 0)));
        Slot slot5 = new Slot(LocalDate.of(2024, 1, 20), new ServiceTime(LocalTime.of(20, 0), LocalTime.of(22, 0)));
        Slot slot6 = new Slot(LocalDate.of(2024, 1, 21), new ServiceTime(LocalTime.of(8, 0), LocalTime.of(10, 0)));
        Slot slot7 = new Slot(LocalDate.of(2024, 1, 21), new ServiceTime(LocalTime.of(12, 0), LocalTime.of(13, 0)));
        // Cas où la liste n'est pas vide
        List<Slot> slotList = List.of(slot1, slot2, slot3, slot4, slot5, slot6, slot7);
        Map<String, List<ServiceTime>> resultMap = slotService.confusionList(slotList);
        assertNotNull(resultMap);
        assertTrue(resultMap.containsKey("2024-01-20"));
        assertTrue(resultMap.containsKey("2024-01-21"));
        assertTrue(resultMap.containsKey("2024-01-22"));

        // Cas où la liste est vide
        List<Slot> emptySlotList = new ArrayList<>();
        resultMap = slotService.confusionList(emptySlotList);
        // Écrire des assertions appropriées pour vérifier le résultat
        assertTrue(resultMap.containsKey("Pas de service réçu"));
        // Cas où la liste a un seul élément
        List<Slot> singleSlotList =List.of(slot1);
        resultMap = slotService.confusionList(singleSlotList);
        assertNotNull(resultMap);
        assertEquals(1, resultMap.size());
    }



}
