package com.mereo.slots.slotsService.entity;

import com.mereo.slots.slotsService.entity.ServiceTime;
import com.mereo.slots.slotsService.entity.Slot;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SlotEntityTests {
	@Test
	public void testGettersSlot() {
		LocalDate dateService = LocalDate.of(2024, 1, 20);
		LocalTime beginService = LocalTime.of(8, 0);
		LocalTime endService = LocalTime.of(17, 0);
		ServiceTime serviceTime = new ServiceTime(beginService, endService);
		Slot slot = new Slot(dateService, serviceTime);

		assertEquals(dateService, slot.getDateService(), "getDateService KO");
		assertEquals(serviceTime, slot.getHeureService(), "getHeureService KO");
	}

	@Test
	public void testSetters() {
		Slot slot = new Slot();
		LocalDate newDateService = LocalDate.of(2024, 1, 20);
		LocalTime newBeginService = LocalTime.of(9, 0);
		LocalTime newEndService = LocalTime.of(18, 0);
		ServiceTime serviceTime = new ServiceTime(newBeginService, newEndService);

		slot.setDateService(newDateService);
		slot.setHeureService(serviceTime);

		assertEquals(newDateService, slot.getDateService(), "setDateService KO");
		assertEquals(serviceTime, slot.getHeureService(), "setHeureService KO");
	}
}
