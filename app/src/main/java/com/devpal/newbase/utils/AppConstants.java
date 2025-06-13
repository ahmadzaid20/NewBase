package com.devpal.newbase.utils; // تأكد من أن الحزمة صحيحة

import android.content.Context; // هذا الـ import مهم جداً
import com.devpal.newbase.Models.Country;
import com.devpal.newbase.R; // استيراد R لجلب الموارد

import java.util.ArrayList;
import java.util.List;

/**
 * AppConstants: يوفر بيانات ثابتة للتطبيق، بما في ذلك قائمة الدول.
 * يستخدم Context لجلب أسماء الدول من ملف strings.xml.
 */
public class AppConstants {

    /**
     * يحصل على قائمة بجميع دول العالم مع رموز الاتصال وأطوال أرقام الهاتف.
     *
     * @param context Context للتطبيق، يستخدم لجلب سلاسل نصية من strings.xml.
     * @return قائمة من كائنات Country.
     */
    public static List<Country> getCountries(Context context) {
        List<Country> countries = new ArrayList<>();

        // إضافة الدول مع ربط اسمها بملف strings.xml
        // تم تحديد maxLength بناءً على الأطوال الشائعة للأرقام المحلية
        // وقد تحتاج لتعديلها بدقة أكبر حسب متطلباتك
        countries.add(new Country(context.getString(R.string.country_afghanistan), "Afghanistan", "+93", 9));
        countries.add(new Country(context.getString(R.string.country_albania), "Albania", "+355", 9));
        countries.add(new Country(context.getString(R.string.country_algeria), "Algeria", "+213", 9));
        countries.add(new Country(context.getString(R.string.country_andorra), "Andorra", "+376", 6));
        countries.add(new Country(context.getString(R.string.country_angola), "Angola", "+244", 9));
        countries.add(new Country(context.getString(R.string.country_antigua_and_barbuda), "Antigua and Barbuda", "+1268", 7));
        countries.add(new Country(context.getString(R.string.country_argentina), "Argentina", "+54", 10));
        countries.add(new Country(context.getString(R.string.country_armenia), "Armenia", "+374", 8));
        countries.add(new Country(context.getString(R.string.country_australia), "Australia", "+61", 9));
        countries.add(new Country(context.getString(R.string.country_austria), "Austria", "+43", 10));
        countries.add(new Country(context.getString(R.string.country_azerbaijan), "Azerbaijan", "+994", 9));
        countries.add(new Country(context.getString(R.string.country_bahamas), "Bahamas", "+1242", 7));
        countries.add(new Country(context.getString(R.string.country_bahrain), "Bahrain", "+973", 8));
        countries.add(new Country(context.getString(R.string.country_bangladesh), "Bangladesh", "+880", 10));
        countries.add(new Country(context.getString(R.string.country_barbados), "Barbados", "+1246", 7));
        countries.add(new Country(context.getString(R.string.country_belarus), "Belarus", "+375", 9));
        countries.add(new Country(context.getString(R.string.country_belgium), "Belgium", "+32", 9));
        countries.add(new Country(context.getString(R.string.country_belize), "Belize", "+501", 7));
        countries.add(new Country(context.getString(R.string.country_benin), "Benin", "+229", 8));
        countries.add(new Country(context.getString(R.string.country_bhutan), "Bhutan", "+975", 7));
        countries.add(new Country(context.getString(R.string.country_bolivia), "Bolivia", "+591", 8));
        countries.add(new Country(context.getString(R.string.country_bosnia_and_herzegovina), "Bosnia and Herzegovina", "+387", 8));
        countries.add(new Country(context.getString(R.string.country_botswana), "Botswana", "+267", 7));
        countries.add(new Country(context.getString(R.string.country_brazil), "Brazil", "+55", 11));
        countries.add(new Country(context.getString(R.string.country_brunei), "Brunei", "+673", 7));
        countries.add(new Country(context.getString(R.string.country_bulgaria), "Bulgaria", "+359", 9));
        countries.add(new Country(context.getString(R.string.country_burkina_faso), "Burkina Faso", "+226", 8));
        countries.add(new Country(context.getString(R.string.country_burundi), "Burundi", "+257", 8));
        countries.add(new Country(context.getString(R.string.country_cabo_verde), "Cabo Verde", "+238", 7));
        countries.add(new Country(context.getString(R.string.country_cambodia), "Cambodia", "+855", 9));
        countries.add(new Country(context.getString(R.string.country_cameroon), "Cameroon", "+237", 9));
        countries.add(new Country(context.getString(R.string.country_canada), "Canada", "+1", 10));
        countries.add(new Country(context.getString(R.string.country_central_african_republic), "Central African Republic", "+236", 8));
        countries.add(new Country(context.getString(R.string.country_chad), "Chad", "+235", 8));
        countries.add(new Country(context.getString(R.string.country_chile), "Chile", "+56", 9));
        countries.add(new Country(context.getString(R.string.country_china), "China", "+86", 11));
        countries.add(new Country(context.getString(R.string.country_colombia), "Colombia", "+57", 10));
        countries.add(new Country(context.getString(R.string.country_comoros), "Comoros", "+269", 7));
        countries.add(new Country(context.getString(R.string.country_congo_dr), "Congo (DR)", "+243", 9));
        countries.add(new Country(context.getString(R.string.country_congo_republic), "Congo (Republic)", "+242", 9));
        countries.add(new Country(context.getString(R.string.country_costa_rica), "Costa Rica", "+506", 8));
        countries.add(new Country(context.getString(R.string.country_cote_divoire), "Cote d'Ivoire", "+225", 8));
        countries.add(new Country(context.getString(R.string.country_croatia), "Croatia", "+385", 9));
        countries.add(new Country(context.getString(R.string.country_cuba), "Cuba", "+53", 8));
        countries.add(new Country(context.getString(R.string.country_cyprus), "Cyprus", "+357", 8));
        countries.add(new Country(context.getString(R.string.country_czech_republic), "Czech Republic", "+420", 9));
        countries.add(new Country(context.getString(R.string.country_denmark), "Denmark", "+45", 8));
        countries.add(new Country(context.getString(R.string.country_djibouti), "Djibouti", "+253", 8));
        countries.add(new Country(context.getString(R.string.country_dominica), "Dominica", "+1767", 7));
        countries.add(new Country(context.getString(R.string.country_dominican_republic), "Dominican Republic", "+1809", 10));
        countries.add(new Country(context.getString(R.string.country_ecuador), "Ecuador", "+593", 9));
        countries.add(new Country(context.getString(R.string.country_egypt), "Egypt", "+20", 10));
        countries.add(new Country(context.getString(R.string.country_el_salvador), "El Salvador", "+503", 8));
        countries.add(new Country(context.getString(R.string.country_equatorial_guinea), "Equatorial Guinea", "+240", 9));
        countries.add(new Country(context.getString(R.string.country_eritrea), "Eritrea", "+291", 7));
        countries.add(new Country(context.getString(R.string.country_estonia), "Estonia", "+372", 8));
        countries.add(new Country(context.getString(R.string.country_eswatini), "Eswatini", "+268", 8));
        countries.add(new Country(context.getString(R.string.country_ethiopia), "Ethiopia", "+251", 9));
        countries.add(new Country(context.getString(R.string.country_fiji), "Fiji", "+679", 7));
        countries.add(new Country(context.getString(R.string.country_finland), "Finland", "+358", 9));
        countries.add(new Country(context.getString(R.string.country_france), "France", "+33", 9));
        countries.add(new Country(context.getString(R.string.country_gabon), "Gabon", "+241", 7));
        countries.add(new Country(context.getString(R.string.country_gambia), "Gambia", "+220", 7));
        countries.add(new Country(context.getString(R.string.country_georgia), "Georgia", "+995", 9));
        countries.add(new Country(context.getString(R.string.country_germany), "Germany", "+49", 10));
        countries.add(new Country(context.getString(R.string.country_ghana), "Ghana", "+233", 9));
        countries.add(new Country(context.getString(R.string.country_greece), "Greece", "+30", 10));
        countries.add(new Country(context.getString(R.string.country_grenada), "Grenada", "+1473", 7));
        countries.add(new Country(context.getString(R.string.country_guatemala), "Guatemala", "+502", 8));
        countries.add(new Country(context.getString(R.string.country_guinea), "Guinea", "+224", 8));
        countries.add(new Country(context.getString(R.string.country_guinea_bissau), "Guinea-Bissau", "+245", 7));
        countries.add(new Country(context.getString(R.string.country_guyana), "Guyana", "+592", 7));
        countries.add(new Country(context.getString(R.string.country_haiti), "Haiti", "+509", 8));
        countries.add(new Country(context.getString(R.string.country_honduras), "Honduras", "+504", 8));
        countries.add(new Country(context.getString(R.string.country_hungary), "Hungary", "+36", 9));
        countries.add(new Country(context.getString(R.string.country_iceland), "Iceland", "+354", 7));
        countries.add(new Country(context.getString(R.string.country_india), "India", "+91", 10));
        countries.add(new Country(context.getString(R.string.country_indonesia), "Indonesia", "+62", 10));
        countries.add(new Country(context.getString(R.string.country_iran), "Iran", "+98", 10));
        countries.add(new Country(context.getString(R.string.country_iraq), "Iraq", "+964", 10));
        countries.add(new Country(context.getString(R.string.country_ireland), "Ireland", "+353", 9));
        countries.add(new Country(context.getString(R.string.country_israel), "Israel", "+972", 9));
        countries.add(new Country(context.getString(R.string.country_italy), "Italy", "+39", 10));
        countries.add(new Country(context.getString(R.string.country_jamaica), "Jamaica", "+1876", 7));
        countries.add(new Country(context.getString(R.string.country_japan), "Japan", "+81", 10));
        countries.add(new Country(context.getString(R.string.country_jordan), "Jordan", "+962", 9));
        countries.add(new Country(context.getString(R.string.country_kazakhstan), "Kazakhstan", "+7", 10));
        countries.add(new Country(context.getString(R.string.country_kenya), "Kenya", "+254", 9));
        countries.add(new Country(context.getString(R.string.country_kiribati), "Kiribati", "+686", 8));
        countries.add(new Country(context.getString(R.string.country_korea_north), "North Korea", "+850", 7));
        countries.add(new Country(context.getString(R.string.country_korea_south), "South Korea", "+82", 10));
        countries.add(new Country(context.getString(R.string.country_kosovo), "Kosovo", "+383", 8));
        countries.add(new Country(context.getString(R.string.country_kuwait), "Kuwait", "+965", 8));
        countries.add(new Country(context.getString(R.string.country_kyrgyzstan), "Kyrgyzstan", "+996", 9));
        countries.add(new Country(context.getString(R.string.country_laos), "Laos", "+856", 8));
        countries.add(new Country(context.getString(R.string.country_latvia), "Latvia", "+371", 8));
        countries.add(new Country(context.getString(R.string.country_lebanon), "Lebanon", "+961", 8));
        countries.add(new Country(context.getString(R.string.country_lesotho), "Lesotho", "+266", 8));
        countries.add(new Country(context.getString(R.string.country_liberia), "Liberia", "+231", 7));
        countries.add(new Country(context.getString(R.string.country_libya), "Libya", "+218", 9));
        countries.add(new Country(context.getString(R.string.country_liechtenstein), "Liechtenstein", "+423", 7));
        countries.add(new Country(context.getString(R.string.country_lithuania), "Lithuania", "+370", 8));
        countries.add(new Country(context.getString(R.string.country_luxembourg), "Luxembourg", "+352", 9));
        countries.add(new Country(context.getString(R.string.country_madagascar), "Madagascar", "+261", 9));
        countries.add(new Country(context.getString(R.string.country_malawi), "Malawi", "+265", 9));
        countries.add(new Country(context.getString(R.string.country_malaysia), "Malaysia", "+60", 9));
        countries.add(new Country(context.getString(R.string.country_maldives), "Maldives", "+960", 7));
        countries.add(new Country(context.getString(R.string.country_mali), "Mali", "+223", 8));
        countries.add(new Country(context.getString(R.string.country_malta), "Malta", "+356", 8));
        countries.add(new Country(context.getString(R.string.country_marshall_islands), "Marshall Islands", "+692", 7));
        countries.add(new Country(context.getString(R.string.country_mauritania), "Mauritania", "+222", 8));
        countries.add(new Country(context.getString(R.string.country_mauritius), "Mauritius", "+230", 7));
        countries.add(new Country(context.getString(R.string.country_mexico), "Mexico", "+52", 10));
        countries.add(new Country(context.getString(R.string.country_micronesia), "Micronesia", "+691", 7));
        countries.add(new Country(context.getString(R.string.country_moldova), "Moldova", "+373", 8));
        countries.add(new Country(context.getString(R.string.country_monaco), "Monaco", "+377", 9));
        countries.add(new Country(context.getString(R.string.country_mongolia), "Mongolia", "+976", 8));
        countries.add(new Country(context.getString(R.string.country_montenegro), "Montenegro", "+382", 8));
        countries.add(new Country(context.getString(R.string.country_morocco), "Morocco", "+212", 9));
        countries.add(new Country(context.getString(R.string.country_mozambique), "Mozambique", "+258", 9));
        countries.add(new Country(context.getString(R.string.country_myanmar), "Myanmar", "+95", 9));
        countries.add(new Country(context.getString(R.string.country_namibia), "Namibia", "+264", 9));
        countries.add(new Country(context.getString(R.string.country_nauru), "Nauru", "+674", 7));
        countries.add(new Country(context.getString(R.string.country_nepal), "Nepal", "+977", 10));
        countries.add(new Country(context.getString(R.string.country_netherlands), "Netherlands", "+31", 9));
        countries.add(new Country(context.getString(R.string.country_new_zealand), "New Zealand", "+64", 9));
        countries.add(new Country(context.getString(R.string.country_nicaragua), "Nicaragua", "+505", 8));
        countries.add(new Country(context.getString(R.string.country_niger), "Niger", "+227", 8));
        countries.add(new Country(context.getString(R.string.country_nigeria), "Nigeria", "+234", 10));
        countries.add(new Country(context.getString(R.string.country_north_macedonia), "North Macedonia", "+389", 8));
        countries.add(new Country(context.getString(R.string.country_norway), "Norway", "+47", 8));
        countries.add(new Country(context.getString(R.string.country_oman), "Oman", "+968", 8));
        countries.add(new Country(context.getString(R.string.country_pakistan), "Pakistan", "+92", 10));
        countries.add(new Country(context.getString(R.string.country_palau), "Palau", "+680", 7));
        countries.add(new Country(context.getString(R.string.country_palestine), "Palestine", "+970", 9)); // فلسطين
        countries.add(new Country(context.getString(R.string.country_panama), "Panama", "+507", 8));
        countries.add(new Country(context.getString(R.string.country_papua_new_guinea), "Papua New Guinea", "+675", 8));
        countries.add(new Country(context.getString(R.string.country_paraguay), "Paraguay", "+595", 9));
        countries.add(new Country(context.getString(R.string.country_peru), "Peru", "+51", 9));
        countries.add(new Country(context.getString(R.string.country_philippines), "Philippines", "+63", 10));
        countries.add(new Country(context.getString(R.string.country_poland), "Poland", "+48", 9));
        countries.add(new Country(context.getString(R.string.country_portugal), "Portugal", "+351", 9));
        countries.add(new Country(context.getString(R.string.country_qatar), "Qatar", "+974", 8));
        countries.add(new Country(context.getString(R.string.country_romania), "Romania", "+40", 9));
        countries.add(new Country(context.getString(R.string.country_russia), "Russia", "+7", 10));
        countries.add(new Country(context.getString(R.string.country_rwanda), "Rwanda", "+250", 9));
        countries.add(new Country(context.getString(R.string.country_saint_kitts_and_nevis), "Saint Kitts and Nevis", "+1869", 7));
        countries.add(new Country(context.getString(R.string.country_saint_lucia), "Saint Lucia", "+1758", 7));
        countries.add(new Country(context.getString(R.string.country_saint_vincent_and_the_grenadines), "Saint Vincent and the Grenadines", "+1784", 7));
        countries.add(new Country(context.getString(R.string.country_samoa), "Samoa", "+685", 7));
        countries.add(new Country(context.getString(R.string.country_san_marino), "San Marino", "+378", 10));
        countries.add(new Country(context.getString(R.string.country_sao_tome_and_principe), "Sao Tome and Principe", "+239", 7));
        countries.add(new Country(context.getString(R.string.country_saudi_arabia), "Saudi Arabia", "+966", 9));
        countries.add(new Country(context.getString(R.string.country_senegal), "Senegal", "+221", 9));
        countries.add(new Country(context.getString(R.string.country_serbia), "Serbia", "+381", 9));
        countries.add(new Country(context.getString(R.string.country_seychelles), "Seychelles", "+248", 7));
        countries.add(new Country(context.getString(R.string.country_sierra_leone), "Sierra Leone", "+232", 8));
        countries.add(new Country(context.getString(R.string.country_singapore), "Singapore", "+65", 8));
        countries.add(new Country(context.getString(R.string.country_slovakia), "Slovakia", "+421", 9));
        countries.add(new Country(context.getString(R.string.country_slovenia), "Slovenia", "+386", 8));
        countries.add(new Country(context.getString(R.string.country_solomon_islands), "Solomon Islands", "+677", 7));
        countries.add(new Country(context.getString(R.string.country_somalia), "Somalia", "+252", 8));
        countries.add(new Country(context.getString(R.string.country_south_africa), "South Africa", "+27", 9));
        countries.add(new Country(context.getString(R.string.country_south_sudan), "South Sudan", "+211", 9));
        countries.add(new Country(context.getString(R.string.country_spain), "Spain", "+34", 9));
        countries.add(new Country(context.getString(R.string.country_sri_lanka), "Sri Lanka", "+94", 9));
        countries.add(new Country(context.getString(R.string.country_sudan), "Sudan", "+249", 9));
        countries.add(new Country(context.getString(R.string.country_suriname), "Suriname", "+597", 7));
        countries.add(new Country(context.getString(R.string.country_sweden), "Sweden", "+46", 9));
        countries.add(new Country(context.getString(R.string.country_switzerland), "Switzerland", "+41", 9));
        countries.add(new Country(context.getString(R.string.country_syria), "Syria", "+963", 9));
        countries.add(new Country(context.getString(R.string.country_taiwan), "Taiwan", "+886", 9));
        countries.add(new Country(context.getString(R.string.country_tajikistan), "Tajikistan", "+992", 9));
        countries.add(new Country(context.getString(R.string.country_tanzania), "Tanzania", "+255", 9));
        countries.add(new Country(context.getString(R.string.country_thailand), "Thailand", "+66", 9));
        countries.add(new Country(context.getString(R.string.country_timor_leste), "Timor-Leste", "+670", 7));
        countries.add(new Country(context.getString(R.string.country_togo), "Togo", "+228", 8));
        countries.add(new Country(context.getString(R.string.country_tonga), "Tonga", "+676", 7));
        countries.add(new Country(context.getString(R.string.country_trinidad_and_tobago), "Trinidad and Tobago", "+1868", 7));
        countries.add(new Country(context.getString(R.string.country_tunisia), "Tunisia", "+216", 8));
        countries.add(new Country(context.getString(R.string.country_turkey), "Turkey", "+90", 10));
        countries.add(new Country(context.getString(R.string.country_turkmenistan), "Turkmenistan", "+993", 8));
        countries.add(new Country(context.getString(R.string.country_tuvalu), "Tuvalu", "+688", 5));
        countries.add(new Country(context.getString(R.string.country_uganda), "Uganda", "+256", 9));
        countries.add(new Country(context.getString(R.string.country_ukraine), "Ukraine", "+380", 9));
        countries.add(new Country(context.getString(R.string.country_united_arab_emirates), "United Arab Emirates", "+971", 9));
        countries.add(new Country(context.getString(R.string.country_united_kingdom), "United Kingdom", "+44", 10));
        countries.add(new Country(context.getString(R.string.country_united_states), "United States", "+1", 10));
        countries.add(new Country(context.getString(R.string.country_uruguay), "Uruguay", "+598", 8));
        countries.add(new Country(context.getString(R.string.country_uzbekistan), "Uzbekistan", "+998", 9));
        countries.add(new Country(context.getString(R.string.country_vanuatu), "Vanuatu", "+678", 7));
        countries.add(new Country(context.getString(R.string.country_vatican_city), "Vatican City", "+379", 10));
        countries.add(new Country(context.getString(R.string.country_venezuela), "Venezuela", "+58", 10));
        countries.add(new Country(context.getString(R.string.country_vietnam), "Vietnam", "+84", 9));
        countries.add(new Country(context.getString(R.string.country_yemen), "Yemen", "+967", 9));
        countries.add(new Country(context.getString(R.string.country_zambia), "Zambia", "+260", 9));
        countries.add(new Country(context.getString(R.string.country_zimbabwe), "Zimbabwe", "+263", 9));

        return countries;
    }
}
