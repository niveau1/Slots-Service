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
        List<ServiceTime> serviceTimes = new ArrayList<>();

        if (slotList.isEmpty()) {
            resultMap.put("Pas de service réçu", serviceTimes);
        } else {
            for (int i = 0; i < slotList.size(); i++) {
                Slot slot = slotList.get(i);
                //Recupere la date de service comme la clé
                key = dateFormat.format(slot.getDateService());
                //Recupere le serviceTime de slot actuel
                ServiceTime currentServiceTime = new ServiceTime(
                        slot.getHeureService().getBeginService(),
                        slot.getHeureService().getEndService()
                );
                //si c'est le permier element on aliment le ArrayList dans le map directement
                if(i==0) {
                    serviceTimes.add(currentServiceTime);
                }
                else {
                    // comme ce n'est pas le permier element on verifier si la Date deja existe dans le map
                    // dans le map existe : {
                    //    "2024-01-24": [
                    //        {
                    //            "beginService": "08:00:00",
                    //            "endService": "15:00:00"
                    //        }
                    //    ]
                    //} et notre objet actuel possede la meme date de service comme "2024-01-24"
                    if (resultMap.containsKey(key)) {
                        //recuperer la liste correspond le key
                        serviceTimes = resultMap.get(key);
                        //recuperer la dernier element dans la liste pour faire la comparation
                        ServiceTime serviceTimeDernierAjoute = serviceTimes.get(serviceTimes.size() - 1);
                        if (!serviceTimeDernierAjoute.getEndService().isBefore(slot.getHeureService().getBeginService())) {
                            serviceTimeDernierAjoute.setEndService(slot.getHeureService().getEndService());
                            //update le dernier elment
                            resultMap.get(key).set(serviceTimes.size() - 1, serviceTimeDernierAjoute);
                        }
                        else {
                            //sinon ajouter l'element
                            //dans le cas
                            /*
                            {
                                "dateService": "2024-01-20",
                                    "heureService": {
                                "beginService": "15:00",
                                        "endService": "18:00"
                            }
                            },
                            {
                                "dateService": "2024-01-20",
                                    "heureService": {
                                "beginService": "20:00",
                                        "endService": "22:00"
                            }
                            }
                            */
                            serviceTimes.add(currentServiceTime);
                        }
                    }
                    else{
                        // si c'est un dateService existe pas dans le map
                        // alors on va vider le liste et l'alimente avec le data serviceTime de element actuel
                        serviceTimes = new ArrayList<>();
                        serviceTimes.add(currentServiceTime);
                    }
                }
                resultMap.put(key, serviceTimes);
            }
        }
        return resultMap;
    }
}
