package com.stylight.productcatalogue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stylight.productcatalogue.dto.ItemDetailsDTO;
import com.stylight.productcatalogue.serviceimpl.CurrencyConverterServiceImpl;
import com.tunyk.currencyconverter.api.CurrencyConverterException;

/**
 * A controller that takes a JSON array and a target currency in String as input.
 * Returns a JSON array with converted price.
 *
 */
@RestController
@RequestMapping("/currencyconverter")
public class CurrencyConverterController {
	
	@Autowired
	private CurrencyConverterServiceImpl currencyConvertorServiceImpl;

	/**
	 * @param inputItemList
	 * @param currency
	 * @return convertedList
	 * @throws CurrencyConverterException 
	 * @throws NumberFormatException 
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/convertCurrency", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity convert(@RequestBody final List<ItemDetailsDTO> inputItemList,
			@RequestParam("currency") final String currency) throws NumberFormatException, CurrencyConverterException {
		if(inputItemList == null || inputItemList.isEmpty() || currency == null) {
			return ResponseEntity.badRequest().body("Input JSON and target Currency are required");
		}
		return ResponseEntity.ok(currencyConvertorServiceImpl.convertCurrency(inputItemList, currency));
	}
}
