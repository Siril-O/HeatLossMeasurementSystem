package ua.heatloss.web.utils;

import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.heatloss.domain.Apartment;
import ua.heatloss.services.ApartmentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = "utils")
@Controller
public class UtilController {

    private static final List<String> COUNTRIES = new ArrayList<>(
            Arrays.asList("Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola"
                    , "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba"
                    , "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados"
                    , "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina"
                    , "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam"
                    , "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde"
                    , "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island"
                    , "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the"
                    , "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus"
                    , "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor"
                    , "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia"
                    , "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan"
                    , "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia"
                    , "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala"
                    , "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)"
                    , "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)"
                    , "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati"
                    , "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan"
                    , "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya"
                    , "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of"
                    , "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique"
                    , "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of",
                    "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique",
                    "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia",
                    "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands",
                    "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines"
                    , "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation"
                    , "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa"
                    , "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone",
                    "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa"
                    , "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena"
                    , "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden"
                    , "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan",
                    "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago",
                    "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
                    "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands",
                    "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)",
                    "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen",
                    "Yugoslavia", "Zambia", "Zimbabwe"));

    private static final List<Term> TERMS = new ArrayList<Term>() {
        {
            int i = 1;
            for (String country : COUNTRIES) {
                add(new Term(i++, country));
            }
        }
    };

    @Autowired
    private ApartmentService apartmentService;

    @RequestMapping(value = "/country", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    List<Term> getCountryTerms(@RequestParam String term) {
        final List<Term> terms = buildResponse(term);
        return terms;
    }

    @RequestMapping(value = "/appartment", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    List<Term> getApartmentNumbersTerms(@RequestParam Integer term) {

        final List<Term> terms = convertToTerms(apartmentService.findApartmentsByNumber(term));
        return terms;
    }

    private List<Term> convertToTerms(List<Apartment> apartments) {
        List<Term> terms = new ArrayList<>();
        for (Apartment apartment : apartments) {
            Term term = new Term(apartment.getId().intValue(), apartment.getNumber().toString());
            terms.add(term);
        }
        return terms;
    }

    private List<Term> buildResponse(String term) {
        return TERMS.stream().filter(tag -> tag.getLabel().contains(term)).collect(Collectors.toList());
    }

}
