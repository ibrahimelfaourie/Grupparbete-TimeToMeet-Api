package com.iot.timetomeet.reservationsummary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservationSummaryDto {

    @SerializedName("sender")
    private String sender;

    @SerializedName("plant_state")
    private PlantState plantState;

    @SerializedName("booking_plant")
    private BookingPlant bookingPlant;

    @SerializedName("booking_details")
    private BookingDetails bookingDetails;

    @SerializedName("booked_conference_rooms_with_price")
    private List<BookedConferenceRoomsWithPrice> bookedConferenceRoomsWithPrice = null;

    @SerializedName("number_of_participants")
    private String numberOfParticipants;

    @SerializedName("bookingFoodBeverage")
    private List<Object> bookingFoodBeverage = null;

    @SerializedName("sum_total_excl_vat")
    private String sumTotalExclVat;

    @SerializedName("booked_tech")
    private List<Object> bookedTech = null;

    @SerializedName("postmarkBool")
    private PostmarkBool postmarkBool;

    @SerializedName("customer_number")
    private String customerNumber;

    @SerializedName("personFirstName")
    private String personFirstName;

    @SerializedName("personLastName")
    private String personLastName;

    @SerializedName("personPhoneNumber")
    private String personPhoneNumber;

    @SerializedName("booked_by_person")
    private BookedByPerson bookedByPerson;

    @SerializedName("booker_invoice_address_or_empty_string")
    private String bookerInvoiceAddressOrEmptyString;

    @SerializedName("specialRequest")
    private String specialRequest;

    @SerializedName("agreement_provider")
    private Object agreementProvider;

    @SerializedName("booking_accept_decline")
    private BookingAcceptDecline bookingAcceptDecline;

    private class BookedByPerson {


        @SerializedName("books_for_organization")
        private String booksForOrganization;

        @SerializedName("books_for_organizationNumber")
        private String booksForOrganizationNumber;

        @SerializedName("email")
        private String email;

    }

    private class BookedConferenceRoomsWithPrice {


        @SerializedName("price")
        private String price;

        @SerializedName("conference_room_title")
        private String conferenceRoomTitle;

        @SerializedName("chosen_seating")
        private String chosenSeating;

    }

    private class BookingAcceptDecline {


        @SerializedName("accept_url")
        private String acceptUrl;

        @SerializedName("decline_url")
        private String declineUrl;

    }

    private class BookingDetails {


        @SerializedName("bookingNumber")
        private String bookingNumber;

        @SerializedName("emailStateText")
        private String emailStateText;

        @SerializedName("arrivalDate")
        private String arrivalDate;

        @SerializedName("arrivalTime")
        private String arrivalTime;

        @SerializedName("departTime")
        private String departTime;

        @SerializedName("blockDescription")
        private String blockDescription;

    }

    private class BookingPlant {


        @SerializedName("online_url")
        private String onlineUrl;

        @SerializedName("logo")
        private Logo logo;

        @SerializedName("confirmation_email")
        private String confirmationEmail;

        @SerializedName("confirmation_phone")
        private String confirmationPhone;

        @SerializedName("name")
        private String name;

        @SerializedName("visiting_address")
        private String visitingAddress;

        @SerializedName("seo_url")
        private String seoUrl;

    }

    private class BookingTTM {

        @SerializedName("true")
        private String _true;

    }

    private class Image {


        @SerializedName("image")
        private String image;

    }

    private class Logo {


        @SerializedName("image")
        private Image image;

    }

    private class NotAgreementCustomer {


        @SerializedName("true")
        private String _true;

    }

    private class PlantState {

        @SerializedName("PreliminaryBooked")
        private PreliminaryBooked preliminaryBooked;

    }

    private class PostmarkBool {

        @SerializedName("bookingTTM")
        private BookingTTM bookingTTM;

        @SerializedName("notAgreementCustomer")
        private NotAgreementCustomer notAgreementCustomer;

    }

    private class PreliminaryBooked {

        @SerializedName("true")
        private String _true;

    }
}

