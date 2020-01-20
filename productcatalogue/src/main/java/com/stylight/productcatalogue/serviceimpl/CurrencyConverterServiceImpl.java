package com.stylight.productcatalogue.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.stylight.productcatalogue.dto.ItemDetailsDTO;
import com.tunyk.currencyconverter.BankUaCom;
import com.tunyk.currencyconverter.api.Currency;
import com.tunyk.currencyconverter.api.CurrencyConverter;
import com.tunyk.currencyconverter.api.CurrencyConverterException;
import com.tunyk.currencyconverter.api.CurrencyNotSupportedException;

@Service
public class CurrencyConverterServiceImpl {

	/**
	 * This method converts currency. It it using
	 * "https://code.google.com/archive/p/currency-converter-api/" for conversion.
	 * 
	 * @param inputItemList
	 * @param currency
	 * @return resultList
	 * @throws NumberFormatException
	 * @throws CurrencyConverterException
	 */
	public List<ItemDetailsDTO> convertCurrency(final List<ItemDetailsDTO> inputItemList, final String currency)
			throws NumberFormatException, CurrencyConverterException {
		List<ItemDetailsDTO> resultList = new ArrayList<>();
		Currency toCurrency = Currency.fromString(currency.toUpperCase());
		CurrencyConverter currencyConverter = new BankUaCom(Currency.USD, toCurrency);
		for (ItemDetailsDTO itemDetailsDTO : inputItemList) {

			if (StringUtils.isAnyBlank(itemDetailsDTO.getItem(), itemDetailsDTO.getCurrency())
					|| null == itemDetailsDTO.getPrice()) {
				System.out.println("Invalid Item: " + itemDetailsDTO.toString());
				continue;
			}

			Currency fromCurrency = null;
			try {
				fromCurrency = Currency.fromString(itemDetailsDTO.getCurrency().toUpperCase());
			} catch (CurrencyNotSupportedException e) {
				System.out.println("Invalid Item: " + itemDetailsDTO.toString());
				continue;
			}

			float convertedCurrencyValue = currencyConverter.convertCurrency(itemDetailsDTO.getPrice().floatValue(),
					fromCurrency, toCurrency);
			ItemDetailsDTO itemToAdd = new ItemDetailsDTO();
			itemToAdd.setItem(itemDetailsDTO.getItem());
			itemToAdd.setCurrency(toCurrency.name());
			itemToAdd.setPrice(BigDecimal.valueOf(convertedCurrencyValue).setScale(2, BigDecimal.ROUND_HALF_EVEN));
			resultList.add(itemToAdd);
		}
		return resultList;
	}

}
