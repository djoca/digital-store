package net.j33r.digitalstore.webapp;

import java.time.Year;
import java.time.YearMonth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CheckoutFormValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return CheckoutForm.class.equals(clazz);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        final CheckoutForm checkoutForm = (CheckoutForm) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cardBrand", null, "Escolha uma bandeira.");

        validateCustomerName(errors, checkoutForm.getCustomerName());
        validateHolder(errors, checkoutForm.getCardHolder());
        validateEmail(errors, checkoutForm.getCustomerEmail());
        validateCardNumber(errors, checkoutForm.getCardNumber());
        validateCardExpiration(errors, checkoutForm.getCardExpirationMonth(), checkoutForm.getCardExpirationYear());
        validateCardCvv(errors, checkoutForm.getCardCvv());
    }

    private void validateCustomerName(final Errors errors, final String customerName) {
        if (StringUtils.isBlank(customerName)) {
            errors.rejectValue("customerName", null, "O nome não pode estar vazio.");
            return;
        }

        if (customerName.length() > 120) {
            errors.rejectValue("customerName", null, "Nome muito longo.");
        }
    }

    private void validateHolder(final Errors errors, final String cardHolder) {
        if (StringUtils.isBlank(cardHolder)) {
            errors.rejectValue("cardHolder", null, "O nome do cartão não pode estar vazio.");
            return;
        }

        if (cardHolder.length() > 25) {
            errors.rejectValue("cardHolder", null, "Nome do cartão inválido.");
        }
    }

    private void validateEmail(final Errors errors, final String customerEmail) {
        if (StringUtils.isBlank(customerEmail)) {
            errors.rejectValue("customerEmail", null, "O e-mail não pode estar vazio.");
            return;
        }

        if (customerEmail.length() > 150) {
            errors.rejectValue("customerName", null, "E-mail muito longo.");
        }

        final Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b");
        final Matcher matcher = pattern.matcher(customerEmail);
        if (!matcher.find()) {
            errors.rejectValue("customerEmail", null, "E-mail inválido.");
        }
    }

    private void validateCardNumber(final Errors errors, final String cardNumber) {
        if (StringUtils.isBlank(cardNumber)) {
            errors.rejectValue("cardNumber", null, "O número do cartão não pode estar vazio.");
            return;
        }

        try {
            final long number = Long.parseLong(cardNumber);
            if (number <= 0) {
                errors.rejectValue("cardNumber", null, "Número de cartão inválido.");
                return;
            }
        } catch (final NumberFormatException e) {
            errors.rejectValue("cardNumber", null, "Número de cartão inválido.");
            return;
        }

        if (cardNumber.length() < 12 || cardNumber.length() > 19) {
            errors.rejectValue("cardNumber", null, "Número de cartão inválido.");
        }

    }

    private void validateCardExpiration(final Errors errors, final String month, final String year) {
        if (StringUtils.isAnyBlank(month, year)) {
            errors.rejectValue("cardExpirationMonth", null, "O mês e o ano de expiração não podem estar vazios.");
            return;
        }

        try {
            final Integer monthValue = Integer.valueOf(month);
            final Integer yearValue = Integer.valueOf(year);

            if (month != null && (monthValue < 1 || monthValue > 12)) {
                errors.rejectValue("cardExpirationMonth", null, "Data de expiração inválida.");
                return;
            }

            if (year != null && (yearValue < Year.now().getValue() || yearValue > Year.MAX_VALUE)) {
                errors.rejectValue("cardExpirationYear", null, "Data de expiração inválida.");
                return;
            }

            if (YearMonth.of(yearValue, monthValue).isBefore(YearMonth.now())) {
                errors.rejectValue("cardExpirationMonth", null, "Data de expiração inválida.");
            }
        } catch (final NumberFormatException e) {
            errors.rejectValue("cardExpirationMonth", null, "Data de expiração inválida.");
        }
    }

    private void validateCardCvv(final Errors errors, final String cardCvv) {
        if (StringUtils.isBlank(cardCvv)) {
            errors.rejectValue("cardCvv", null, "O CVV não pode estar vazio.");
            return;
        }

        if (cardCvv.length() < 3 || cardCvv.length() > 4) {
            errors.rejectValue("cardCvv", null, "CVV inválido.");
            return;
        }

        try {
            Integer.parseInt(cardCvv);
        } catch (final NumberFormatException e) {
            errors.rejectValue("cardCvv", null, "CVV inválido.");
        }

    }

}
