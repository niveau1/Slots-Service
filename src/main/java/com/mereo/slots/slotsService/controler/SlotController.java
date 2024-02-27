package com.mereo.slots.slotsService.controler;

import com.mereo.slots.slotsService.entity.ServiceTime;
import com.mereo.slots.slotsService.entity.Slot;
import com.mereo.slots.slotsService.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class SlotController {

    private SlotService slotService;
    @Autowired
    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping(value = "/serviceDispo")
    public Map<String, List<ServiceTime>> getServiceDispo(@RequestBody List<Slot> slotList) {
        List<Slot> reversedSlotList = slotService.reversList(slotList);
        Map<String, List<ServiceTime>> listConfusion = slotService.confusionList(reversedSlotList);
        // Triez la TreeMap par ordre naturel des clés (dates)
        // Parce dans la partie JAVA la code fonctionne bien
        // mais pour la partie Json sur PostMan l'ordre de la date n'est pas bon
        Map<String, List<ServiceTime>> sortedListConfusion = new TreeMap<>(listConfusion);
        return sortedListConfusion;
    }
}
