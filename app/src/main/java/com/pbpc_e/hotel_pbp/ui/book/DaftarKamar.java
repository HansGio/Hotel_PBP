package com.pbpc_e.hotel_pbp.ui.book;

import java.util.ArrayList;

public class DaftarKamar {
    public ArrayList<Kamar> Kamar;

    public DaftarKamar(){
        Kamar = new ArrayList();
        Kamar.add(Deluxe);
        Kamar.add(Executive);
        Kamar.add(Premier);
        Kamar.add(Classic);
        Kamar.add(Cloude);

    }


    public static final Kamar Deluxe = new Kamar("Deluxe Room", "Surround yourself with simple " +
            "elegance in this 40 square meter beautifully appointed Deluxe Room. Equipped with large LED screens and high speed " +
            "broadband internet access, guests staying in the Deluxe Room also has complimentary access to Gaharu Spa and Fitness. " +
            "Smoking and non smoking rooms are available on request, however will only be confirmed at check-in.",
            "AC", 1100000,
            "https://asset-a.grid.id/crop/0x0:0x0/360x240/photo/nationalgeographic/201710051442766_b.jpg"
    );

    public static final Kamar Executive = new Kamar("Executive Room", "Surround yourself with simple " +
            "elegance in this 40 square meter beautifully appointed Deluxe Room. Equipped with large LED screens and high speed " +
            "broadband internet access, guests staying in the Deluxe Room also has complimentary access to Gaharu Spa and Fitness. " +
            "Smoking and non smoking rooms are available on request, however will only be confirmed at check-in.",
            "AC, Breakfast", 1900000,
            "https://pix10.agoda.net/hotelImages/1061034/-1/e848e7a853da2870cbe38ac4f8f01fd8.jpg?s=1024x768"
    );

    public static final Kamar Premier = new Kamar("Premier Room", "Surround yourself with simple " +
            "elegance in this 40 square meter beautifully appointed Deluxe Room. Equipped with large LED screens and high speed " +
            "broadband internet access, guests staying in the Deluxe Room also has complimentary access to Gaharu Spa and Fitness. " +
            "Smoking and non smoking rooms are available on request, however will only be confirmed at check-in.",
            "AC, WiFi", 2900000,
            "https://cf.bstatic.com/images/hotel/max1024x768/625/62570076.jpg"
    );

    public static final Kamar Classic = new Kamar("Classic Room", "Surround yourself with simple " +
            "elegance in this 40 square meter beautifully appointed Deluxe Room. Equipped with large LED screens and high speed " +
            "broadband internet access, guests staying in the Deluxe Room also has complimentary access to Gaharu Spa and Fitness. " +
            "Smoking and non smoking rooms are available on request, however will only be confirmed at check-in.",
            "AC, WiFi, Breakfast", 4900000,
            "https://asset.kompas.com/crops/qlKhSCExt0OA2oDO6QsopXCdHj0=/209x0:979x385/750x500/data/photo/2018/07/24/3656116093.jpg"
    );

    public static final Kamar Cloude = new Kamar("Cloude Suite", "Surround yourself with simple " +
            "elegance in this 40 square meter beautifully appointed Deluxe Room. Equipped with large LED screens and high speed " +
            "broadband internet access, guests staying in the Deluxe Room also has complimentary access to Gaharu Spa and Fitness. " +
            "Smoking and non smoking rooms are available on request, however will only be confirmed at check-in.",
            "AC, WiFi, Breakfast", 6900000,
            "https://cdn-2.tstatic.net/tribunnews/foto/bank/images/hotel-puncak_20160511_212234.jpg"
    );



}
