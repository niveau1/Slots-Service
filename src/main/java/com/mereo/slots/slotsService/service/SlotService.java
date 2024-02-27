package com.mereo.slots.slotsService.service;

import com.mereo.slots.slotsService.entity.ServiceTime;
import com.mereo.slots.slotsService.entity.Slot;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SlotService {
    public List<Slot> reversList(List<Slot> slotList) {
        // Trier le liste d'abord par la date et l'heure dans le but de donner la methode confusionList une liste trié
        List<Slot> soltsService = new ArrayList<>(slotList);
        Collections.sort(soltsService, new Comparator<Slot>() {
            public int compare(Slot s1, Slot s2) {
                int result = s1.getDateService().compareTo(s2.getDateService());
                if (result == 0) {
                    return s1.getHeureService().getBeginService().compareTo(s2.getHeureService().getBeginService());
                }
                return result;
            }
        });
        return soltsService;
    }

    public Map<String, List<ServiceTime>> confusionList(List<Slot> slotList) {
        /*
        Slot slot1 = new Slot(LocalDate.of(2024, 1, 20), new ServiceTime(LocalTime.of(8, 0), LocalTime.of(10, 0)));
        Slot slot2 = new Slot(LocalDate.of(2024, 1, 23), new ServiceTime(LocalTime.of(8, 0), LocalTime.of(12, 0)));
        Slot slot3 = new Slot(LocalDate.of(2024, 1, 20), new ServiceTime(LocalTime.of(14, 0), LocalTime.of(15, 0)));
        Slot slot4 = new Slot(LocalDate.of(2024, 1, 22), new ServiceTime(LocalTime.of(15, 0), LocalTime.of(18, 0)));
        Slot slot5 = new Slot(LocalDate.of(2024, 1, 20), new ServiceTime(LocalTime.of(20, 0), LocalTime.of(22, 0)));

        Slot slot6 = new Slot(LocalDate.of(2024, 1, 21), new ServiceTime(LocalTime.of(8, 0), LocalTime.of(10, 0)));
        Slot slot7 = new Slot(LocalDate.of(2024, 1, 21), new ServiceTime(LocalTime.of(12, 0), LocalTime.of(13, 0)));
        */
        Map<String, List<ServiceTime>> resultMap = new HashMap<>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String key = "";
        ServiceTime currentServiceTime = new ServiceTime();
        List<ServiceTime> serviceTimes = new ArrayList<>();

        if (slotList.size() == 1) {
            Slot slot = slotList.get(0);
            key = dateFormat.format(slot.getDateService());
            currentServiceTime = new ServiceTime(
                    slot.getHeureService().getBeginService(),
                    slot.getHeureService().getEndService()
            );
            serviceTimes.add(currentServiceTime);
            resultMap.put(key, serviceTimes);
        }
        else if(slotList.isEmpty()) {
            resultMap.put("Pas de service réçu", serviceTimes);
        }
        else {
        for (int i = 0; i < slotList.size(); i++) {
            Slot slot1 = slotList.get(i); //preparer le premier element pour faire la comparaison

            key = dateFormat.format(slot1.getDateService());
            serviceTimes = resultMap.computeIfAbsent(key, k -> new ArrayList<>());

            currentServiceTime = new ServiceTime(
                    slot1.getHeureService().getBeginService(),
                    slot1.getHeureService().getEndService()
            );

            // Parcourir les éléments suivants pour fusionner les plages horaires si les 2 element ont la meme date
            for (int j = i + 1; j < slotList.size(); j++) {
                Slot slot2 = slotList.get(j);

                if (slot1.getDateService().equals(slot2.getDateService())) {
                    if (slot1.getHeureService().getEndService().isBefore(slot2.getHeureService().getBeginService())) {
                        break; // Arrêter si la plage horaire suivante ne peut pas être fusionnée
                    } else {
                        currentServiceTime.setEndService(slot2.getHeureService().getEndService());
                        i = j;
                    }
                } else {
                    break; // Arrêter si les dates sont différentes
                }
            }
            serviceTimes.add(currentServiceTime);
        }
        }
        return resultMap;
    }
}
