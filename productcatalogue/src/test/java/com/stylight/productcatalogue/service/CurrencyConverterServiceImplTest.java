package com.stylight.productcatalogue.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stylight.productcatalogue.dto.ItemDetailsDTO;
import com.stylight.productcatalogue.serviceimpl.CurrencyConverterServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyConverterServiceImplTest {

	@Spy
	private CurrencyConverterServiceImpl currencyConverterServiceImpl;
	@Mock
	private ItemDetailsDTO itemDetailsDTOMock;

	@Test
	public void testCurrencyConverterReturnsEmptyList() throws Exception {
		List<ItemDetailsDTO> itemList = new ArrayList<>();
		itemList.add(itemDetailsDTOMock);
		List<ItemDetailsDTO> resultList = currencyConverterServiceImpl.convertCurrency(itemList, "EUR");
		assertTrue(resultList.size() == 0);
	}

	@Test
	public void testCurrencyConverter() throws Exception {
		List<ItemDetailsDTO> itemList = new ArrayList<>();
		itemList.add(itemDetailsDTOMock);
		when(itemDetailsDTOMock.getItem()).thenReturn("A");
		when(itemDetailsDTOMock.getCurrency()).thenReturn("USD");
		when(itemDetailsDTOMock.getPrice()).thenReturn(new BigDecimal(9.87));
		List<ItemDetailsDTO> resultList = currencyConverterServiceImpl.convertCurrency(itemList, "EUR");
		assertTrue(resultList.size() == 1);
		assertTrue(resultList.get(0).getCurrency() == "EUR");
		assertTrue(resultList.get(0).getItem() == "A");
		assertEquals(resultList.get(0).getPrice(), new BigDecimal(8.94).setScale(2, BigDecimal.ROUND_HALF_EVEN));
	}

}
