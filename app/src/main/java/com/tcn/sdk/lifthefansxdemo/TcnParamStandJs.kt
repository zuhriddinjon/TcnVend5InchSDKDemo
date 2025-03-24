package com.tcn.sdk.lifthefansxdemo

object TcnParamStandJs {

    val STAND_ITEM_QUERY: Array<String> by lazy {
        arrayOf(
            "0~Umumiy so‘rov, VMC hozirda yuborilishi kerak bo‘lgan parametrlarni yuboradi, keyingi SN o‘zgarganda avvalgi yuborilmaydi",
            "1~Daromad summasini so‘rash",
            "2~Tugmalarni so‘rash",
            "3~Eshik ochiq-yopiq holatini so‘rash",
            "4~Harorat nazorati holatini so‘rash",
            "5~Yoritish holatini so‘rash",
            "6~Shisha eshikni bug‘dan tozalash holatini so‘rash",
            "7~Nurni aniqlash holatini so‘rash",
            "8~Tovar kanali holatini so‘rash",
            "9~MDB tashqi qurilma holatini so‘rash",
            "200~Barcha holatlarni boshqaruv platasidan majburiy javob olish"
        )
    }

    val STAND_ITEM_PARAM_QUERY: Array<String> by lazy {
        arrayOf(
            "CHTP~0~Barcha harorat nazorati parametrlarini so‘rash",
            "CHTP~1~Harorat nazorati rejimi",
            "CHTP~2~Maqsad harorati",
            "CHTP~3~Muzdan tushirish vaqti",
            "CHTP~4~Doimiy ishlash vaqti",
            "CHLEDP~0~Barcha yoritish parametrlarini so‘rash",
            "CHLEDP~1~Yoritish tugmasi",
            "CHLEDP~2~Quvvat chiqishi foizi",
            "CHGLP~0~Barcha shisha bug‘ tozalash parametrlarini so‘rash",
            "CHGLP~1~Shisha bug‘ tozalash tugmasi",
            "CHGLP~2~Quvvat chiqishi foizi",
            "CHMDBP~0~Barcha MDB qurilmalari parametrlarini so‘rash",
            "CHMDBP~1~Tanga qabul qilgich parametrlari",
            "CHMDBP~2~Banknota qabul qilgich parametrlari",
            "CHMDBP~3~Naqd pulsiz qurilmalar parametrlari",
            "CHMDBP~4~Umumiy parametrlar",
            "CHSLP~0~Barcha tovar kanallari parametrlarini so‘rash",
            "CHSLP~1~Tovar kanali joylashuvi",
            "CHSLP~2~Motor tebranish soni",
            "CHSLP~3~Tovar kanali harakat rejimi",
            "CHSLP~4~Tovar kanali kombinatsiya holati"
        )
    }

    val STAND_ITEM_PARAM_SET: Array<String> by lazy {
        arrayOf(
            "TMODE~Harorat nazorati rejimi",
            "TARGET~Maqsad harorati",
            "FROST~Muzdan tushirish vaqti",
            "TWORK~Doimiy ishlash vaqti",
            "LEDL~Yoritish tugmasi",
            "LPOWER~Yoritish quvvat chiqishi foizi",
            "GLHL~Shisha bug‘ tozalash tugmasi",
            "GPOWER~Shisha bug‘ tozalash quvvat chiqishi foizi",
            "COIN~ENABLE~MDB tanga qabul qilgichni yoqish",
            "COIN~CHANGE~MDB tanga qabul qilgich qaytarish tugmasi",
            "BILL~ENABLE~MDB banknota qabul qilgichni yoqish",
            "BILL~CHANGE~MDB banknota qabul qilgich qaytarish tugmasi",
            "BILL~ECROW~MDB banknota qabul qilgich qaytarish usuli",
            "BILL~CHTYPE~MDB banknota qabul qilgich qaytarish turi",
            "CASHLESS~ENABLE~Naqd pulsiz qurilmani yoqish",
            "CASHLESS~ALIMIT~Kiritilgan pul miqdorini cheklash",
            "ARRAY~Tovar kanali joylashuvi",
            "SHAKE~Motor tebranish soni",
            "DRIVE~Tovar kanali harakat rejimi",
            "COMB~Tovar kanali kombinatsiya holati"
        )
    }

    val STAND_ITEM_ACTION: Array<String> by lazy {
        arrayOf(
            "0~Nurni o‘z-o‘zini tekshirish",
            "1~Tovar kanalini tekshirish",
            "2~Oldindan banknota va tangalarni joylash",
            "3~Banknota qaytarish miqdorini tozalash",
            "TestSelectGoods~Tovar tanlash sahifasini sinovdan o‘tkazish",
            "TestShoppingCar~Savatcha orqali tanlash sahifasini sinovdan o‘tkazish",
            "TestPickUpGoodsCode~Mahsulot olish kodini sinovdan o‘tkazish",
            "TestWaitPay~To‘lovni kutish sahifasini sinovdan o‘tkazish",
            "Title~Holat paneli tarkibi",
            "Tips~Tavsiya oynasi tarkibi",
            "Update~Haydovchi dasturini yangilash"
        )
    }
}
