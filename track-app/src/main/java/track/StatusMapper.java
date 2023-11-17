package track;

import org.apache.commons.lang3.StringUtils;
import track.model.DeliveryStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static track.model.DeliveryStatus.*;

public class StatusMapper {

    // z InPost api object "origin_status": key = "name", value = "description"
    public static final Map<String, String> inPostStatusMap = initInPostStatusesMap();

    private StatusMapper() {
        // do nothing
    }

    public static DeliveryStatus toDeliveryStatusMapper(String inPostStatusName) {
        if (StringUtils.isEmpty(inPostStatusName)) {
            return NOT_FOUND;
        }
        if (List.of("created", "offers_prepared", "offer_selected", "confirmed").contains(inPostStatusName)) {
            return CONFIRMED;
        }
        if ("dispatched_by_sender".equals(inPostStatusName)){
            return IN_SHIPPING_PARCEL_LOCKER;
        }
        if (List.of(
                "collected_from_sender", "taken_by_courier", "dispatched_by_sender_to_pok", "taken_by_courier_from_pok"
        ).contains(inPostStatusName)) {
            return HANDED_TO_SHIPPING_COMPANY;
        }
        if (List.of("adopted_at_source_branch", "sent_from_source_branch", "adopted_at_sorting_center", "sent_from_sorting_center", "adopted_at_target_branch", "out_for_delivery", "out_for_delivery_to_address").contains(inPostStatusName)) {
            return ON_THE_ROAD;
        }
        if (List.of("ready_to_pickup", "pickup_reminder_sent").contains(inPostStatusName)) {
            return WAITING_IN_RECEIVING_PARCEL_LOCKER;
        }
        if ("delivered".equals(inPostStatusName)) {
            return DELIVERED;
        }
        return NOT_STANDARD_STAGE;
    }

    public static List<DeliveryStatus> getActiveStatusesList() {
        return List.of(
                NOT_FOUND, CONFIRMED, HANDED_TO_SHIPPING_COMPANY,IN_SHIPPING_PARCEL_LOCKER,
                ON_THE_ROAD, HANDED_OUT_FOR_DELIVERY, WAITING_IN_RECEIVING_PARCEL_LOCKER, NOT_STANDARD_STAGE
        );
    }

    private static Map<String, String> initInPostStatusesMap() {
        final Map<String, String> statusesMap = new HashMap<>(53);
        statusesMap.put("created", "Przesyłka została utworzona, ale nie jest gotowa do nadania.");
        statusesMap.put("offers_prepared", "Oferty dla przesyłki zostały przygotowane.");
        statusesMap.put("offer_selected", "Klient wybrał jedną z zaproponowanych ofert.");
        statusesMap.put("confirmed", "Nadawca poinformował nas, że przygotował przesyłkę do nadania. Podróż przesyłki jeszcze się nie rozpoczęła.");
        statusesMap.put("dispatched_by_sender", "Paczka oczekuje na wyjęcie z automatu Paczkomat przez doręczyciela. Stąd trafi do najbliższego oddziału InPost i wyruszy w trasę do odbiorczego automatu Paczkomat.");
        statusesMap.put("collected_from_sender", "Kurier odebrał paczkę od Nadawcy i przekazuje ją do oddziału InPost.");
        statusesMap.put("taken_by_courier", "Przesyłka została odebrana od Nadawcy i wyruszyła w dalszą drogę.");
        statusesMap.put("adopted_at_source_branch", "Przesyłka trafiła do oddziału InPost, skąd wkrótce wyruszy w dalszą drogę.");
        statusesMap.put("sent_from_source_branch", "Przesyłka jest transportowana między oddziałami InPost.");
        statusesMap.put("ready_to_pickup_from_pok", "Prosimy o odebranie przesyłki z punktu InPost w ciągu 3 dni.");
        statusesMap.put("ready_to_pickup_from_pok_registered", "Prosimy o odebranie przesyłki z punktu InPost w ciągu 3 dni. Adres.");
        statusesMap.put("oversized", "Paczka nie mieści się w skrytce automatu Paczkomat.");
        statusesMap.put("adopted_at_sorting_center", "Przesyłka czeka na przesiadkę do miasta docelowego. W Sortowni Głównej zatrzymuje się na chwilę większość przesyłek InPost. W supernowoczesnym magazynie sortowanych jest nawet milion przesyłek dziennie!");
        statusesMap.put("sent_from_sorting_center", "Przesyłka jedzie do miasta Odbiorcy.");
        statusesMap.put("adopted_at_target_branch", "Przesyłka jest już w mieście Odbiorcy. Wkrótce trafi do rąk doręczyciela i rozpocznie ostatni etap podróży.");
        statusesMap.put("out_for_delivery", "Przesyłka trafi do odbiorcy najpóźniej w najbliższym dniu roboczym. Doręczyciel InPost rozwozi przesyłki nawet do późnych godzin wieczornych, dlatego warto mieć włączony telefon.");
        statusesMap.put("ready_to_pickup", "I gotowe! Paczka czeka na odbiór w wybranym automacie Paczkomat. Odbiorca otrzymuje e-maila, a także powiadomienie w aplikacji InPost Mobile lub SMS-a z kodem odbioru i informacją, jak długo paczka będzie czekać na odbiór. Jeśli paczka nie zostanie odebrana w tym czasie, zostanie zwrócona do Nadawcy, o czym poinformujemy Odbiorcę w osobnych komunikatach.");
        statusesMap.put("pickup_reminder_sent", "Paczka oczekuje w automacie Paczkomat. Będzie tam czekać na Ciebie przez kolejne 24 godziny. Pośpiesz się! Jeśli przesyłka nie zostanie odebrana z automatu Paczkomat, wróci do Nadawcy. Chcesz odpłatnie przedłużyć pobyt przesyłki w automacie Paczkomat? Możesz to zrobić w naszej aplikacji InPost Mobile! Dowiedz się więcej: [https://inpost.pl/pomoc-jak-przedluzyc-termin-odbioru-paczki-w-paczkomacie].");
        statusesMap.put("delivered", "Podróż przesyłki od Nadawcy do Odbiorcy zakończyła się, ale nie musi to oznaczać końca naszej znajomości:) Jeśli lubisz InPost, odwiedź nasz fanpage na Facebooku. Dziękujemy!");
        statusesMap.put("pickup_time_expired", "Czas na odbiór Paczki z automatu Paczkomat już minął. Paczka zostanie zwrócona do Nadawcy. Odbiorca  jeszcze ma szansę odebrać paczkę, jeśli dotrze do automatu Paczkomat przed Doręczycielem InPost.");
        statusesMap.put("avizo", "Kurier InPost ponownie nie zastał Odbiorcy pod wskazanym adresem. Przesyłka wyruszyła w drogę powrotną do Nadawcy.");
        statusesMap.put("claimed", "Prosimy o dokończenie procesu reklamacji poprzez wypełnienie formularza na stronie InPost.");
        statusesMap.put("returned_to_sender", "Przesyłka wyruszyła w drogę powrotną do Nadawcy.");
        statusesMap.put("canceled", "Etykieta nadawcza została anulowana lub utraciła ważność. Przesyłka nie została wysłana do Odbiorcy.");
        statusesMap.put("other", "Przesyłka znajduje się w nierozpoznanym statusie.");
        statusesMap.put("dispatched_by_sender_to_pok", "Nadawca przekazał przesyłkę pracownikowi punktu InPost. Tu rozpoczyna się jej podróż do Odbiorcy.");
        statusesMap.put("out_for_delivery_to_address", "Przesyłka jest już na ostatnim etapie podróży - została przekazana kurierowi w celu dostarczenia pod wskazany adres.");
        statusesMap.put("pickup_reminder_sent_address", "Kurier InPost nie zastał Odbiorcy pod wskazanym adresem. Kolejna próba doręczenia nastąpi w następnym dniu roboczym.");
        statusesMap.put("rejected_by_receiver", "Odbiorca odmówił przyjęcia przesyłki.");
        statusesMap.put("undelivered_wrong_address", "Brak możliwości doręczenia w dniu dzisiejszym. Powód: błędne dane adresowe.");
        statusesMap.put("undelivered_incomplete_address", "Brak możliwości doręczenia w dniu dzisiejszym. Powód: niepełne dane adresowe.");
        statusesMap.put("undelivered_unknown_receiver", "Brak możliwości doręczenia w dniu dzisiejszym. Powód: Odbiorca nieznany.");
        statusesMap.put("undelivered_cod_cash_receiver", "Brak możliwości doręczenia w dniu dzisiejszym - Odbiorca nie miał gotówki do opłacenia kwoty pobrania.");
        statusesMap.put("taken_by_courier_from_pok", "Doręczyciel InPost odebrał przesyłkę nadaną w PaczkoPunkcie i przekazuje ją do oddziału InPost, skąd zostanie wysłana w dalszą drogę.");
        statusesMap.put("undelivered", "Przekazanie do magazynu przesyłek niedoręczalnych.");
        statusesMap.put("return_pickup_confirmation_to_sender", "Przesyłka została odebrana. Zwrotne Potwierdzenie Odbioru zostało wysłane do Nadawcy.");
        statusesMap.put("ready_to_pickup_from_branch", "Jeśli Twoja paczka trafiła do oddziału InPost, skontaktuj się z Infolinią, aby sprawdzić możliwości jej odbioru.");
        statusesMap.put("delay_in_delivery", "Dostawa się opóźni - najmocniej przepraszamy. W kolejnych wiadomościach poinformujemy Odbiorcę o nowym terminie doręczenia.");
        statusesMap.put("redirect_to_box", "Adresat tej paczki kurierskiej skorzystał z darmowej opcji dynamicznego przekierowania do automatu Paczkomat InPost. Po dostarczeniu przesyłki do wybranej maszyny odbiorca otrzyma wiadomości, dzięki którym, będzie mógł ją odebrać.");
        statusesMap.put("canceled_redirect_to_box", "Przekierowanie tej paczki kurierskiej do automatu Paczkomat InPost okazało się niemożliwe ze względu na zbyt duży gabaryt. Przesyłka zostanie doręczona do Odbiorcy na adres wskazany w zamówieniu.");
        statusesMap.put("readdressed", "Przesyłka kurierska została bezpłatnie przekierowana na inny adres na życzenie Odbiorcy.");
        statusesMap.put("undelivered_no_mailbox", "Brak możliwości doręczenia w dniu dzisiejszym. Powód: brak skrzynki pocztowej.");
        statusesMap.put("undelivered_not_live_address", "Brak możliwości doręczenia w dniu dzisiejszym. Powód: Odbiorca nie mieszka pod wskazanym adresem.");
        statusesMap.put("undelivered_lack_of_access_letterbox", "Paczka wyruszyła w drogę powrotną do Nadawcy.");
        statusesMap.put("missing", "translation missing: pl_PL.statuses.missing.description");
        statusesMap.put("stack_in_customer_service_point", "Kliknij i dowiedz się więcej na temat magazynowania paczek w  PaczkoPunkcie. https://inpost.pl/pomoc-czym-jest-magazynowanie-paczek-w-pop");
        statusesMap.put("stack_parcel_pickup_time_expired", "Upłynął termin odebrania paczki z PaczkoPunktu, ale paczka nadal jest w nim magazynowana - czeka na przyjazd kuriera, który ją zabierze do automatu Paczkomat.");
        statusesMap.put("unstack_from_customer_service_point", "Kurier wiezie Twoją paczkę do automatu Paczkomat.");
        statusesMap.put("courier_avizo_in_customer_service_point", "Paczka została awizowana w PaczkoPunkcie. Jeśli jej nie odbierzesz w ciągu trzech dni roboczych, wróci do Nadawcy.");
        statusesMap.put("taken_by_courier_from_customer_service_point", "Czas na odbiór paczki minął. Została odebrana przez Kuriera z PaczkoPunktu i niebawem wyruszy w podróż powrotną do Nadawcy.");
        statusesMap.put("stack_in_box_machine", "Kliknij i dowiedz się więcej na temat magazynowania paczek w tymczasowych automatach Paczkomat: https://inpost.pl/pomoc-czym-jest-magazynowanie-paczek-w-paczkomatach-tymczasowych");
        statusesMap.put("unstack_from_box_machine", "Czas na odbiór paczki magazynowanej w tymczasowym automacie Paczkomat upłynął. Paczka jest w drodze do pierwotnie wybranego automatu Paczkomat. Poinformujemy Cię, gdy będzie na miejscu.");
        statusesMap.put("stack_parcel_in_box_machine_pickup_time_expired", "Upłynął termin odebrania paczki z automatu Paczkomat tymczasowego, ale paczka nadal jest w nim magazynowana - czeka na przyjazd kuriera, który ją zabierze do pierwotnie wybranego automatu Paczkomat.");
        return statusesMap;
    }
}
