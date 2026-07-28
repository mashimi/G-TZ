package com.example.data.local

import com.example.data.model.LessonEntity
import com.example.data.model.QuizQuestion
import com.example.data.model.VocabItem

object PrepopulatedData {

    val defaultLessons = listOf(
        // ==================== LEVEL A1 (Basic & FREE) ====================
        LessonEntity(
            id = 1,
            level = "A1",
            lessonNumber = 1,
            titleGerman = "Begrüßung & Sich Vorstellen",
            titleSwahili = "Salamu na Kujitambulisha",
            swahiliIntro = "Karibu katika somo la kwanza la Kijerumani! Kama vile Kiswahili kinavyotofautisha 'Habari za asubuhi' na 'Shikamoo', Kijerumani kina salamu rasmi na za kishikaji.",
            grammarExplanationSwahili = "Katika Kijerumani, vitenzi hubadilika kulingana na nafsi (Conjugation). Mfano: 'heißen' (kuitwa) -> Ich heiße (Naitwa), Du heißt (Unaitwa). Vilevile, heshima hutumika sana: 'Sie' (Nyie / Mtu mzima au mgeni).",
            culturalTipSwahili = "Ujerumani, ukimkuta mtu asubuhi kabla ya saa tano ni kawaida kusema 'Guten Morgen'. Unapokutana na mtu usimkumbatie mara moja, kushikana mikono kwa nguvu (firm handshake) ndio utamaduni rasmi.",
            isCompleted = true,
            isLocked = false,
            requiredPlan = "FREE"
        ),
        LessonEntity(
            id = 2,
            level = "A1",
            lessonNumber = 2,
            titleGerman = "Zahlen 1 bis 100 & Preise",
            titleSwahili = "Nambari 1 hadi 100 na Bei",
            swahiliIntro = "Kujua nambari ni muhimu sana sokoni na unapofanya manunuzi. Kijerumani kina mfumo wa kipekee wa kusoma namba kuanzia 21 kuendelea (Mstari wa nyuma mbele!).",
            grammarExplanationSwahili = "Sawa na Kiswahili unaposema 'Ishirini na Moja', Kijerumani unasema 'einundzwanzig' (moja-na-ishirini!). 'ein' (1) + 'und' (na) + 'zwanzig' (20). Usihofu, utazoea haraka!",
            culturalTipSwahili = "Nchini Ujerumani bei zilizoandikwa madukani tayari zimejumuisha kodi (MwSt / VAT). Hakuna kupunguza bei (bargaining) kama ilivyo sokoni Kariakoo!",
            isCompleted = false,
            isLocked = false,
            requiredPlan = "FREE"
        ),
        LessonEntity(
            id = 3,
            level = "A1",
            lessonNumber = 3,
            titleGerman = "Essen & Trinken im Restaurant",
            titleSwahili = "Chakula na Vinywaji Mkahawani",
            swahiliIntro = "Jifunze jinsi ya kuagiza chakula, kahawa, au chai katika mkahawa wa Kijerumani kwa kutumia lugha ya adabu na heshima.",
            grammarExplanationSwahili = "Unapoagiza chakula tumia Mfumo wa 'Ich möchte...' (Ningependa...) badala ya 'Ich will' (Nataka). Mfano: 'Ich möchte einen Kaffee, bitte' (Ningependa kahawa, tafadhali).",
            culturalTipSwahili = "Ujerumani maji ya kunywa katika migahawa mara nyingi hukuja na gasi (Sprudelwasser/Gas). Kama unataka maji ya kawaida yasiyo na gasi, omba 'Stilles Wasser'.",
            isCompleted = false,
            isLocked = false,
            requiredPlan = "FREE"
        ),
        LessonEntity(
            id = 4,
            level = "A1",
            lessonNumber = 4,
            titleGerman = "Familie & Freunde",
            titleSwahili = "Familia na Rafiki",
            swahiliIntro = "Taja wanafamilia yako kwa Kijerumani: Baba, Mama, Kaka, Dada, na Watoto.",
            grammarExplanationSwahili = "Majina yote katika Kijerumani yana jinsia ya kisarufi (Nomen mit Artikel): Der (Kiume/MME), Die (Kike/MKE), Das (Kati/NEUTER). Mfano: der Vater (baba), die Mutter (mama), das Kind (mtoto).",
            culturalTipSwahili = "Familia za Kijerumani kwa ujumla ni ndogo ikilinganishwa na familia za Kitanzania. Lakini heshima na maadili ya familia hutiliwa mkazo sana.",
            isCompleted = false,
            isLocked = false,
            requiredPlan = "FREE"
        ),
        LessonEntity(
            id = 5,
            level = "A1",
            lessonNumber = 5,
            titleGerman = "Uhrzeit & Tagesablauf",
            titleSwahili = "Muda na Ratiba ya Siku",
            swahiliIntro = "Kusema muda na saa kwa Kijerumani, na kueleza ratiba yako ya kila siku.",
            grammarExplanationSwahili = "Saa kwa Kijerumani inatumika mfumo wa saa 24 rasmi au saa 12 kijamii. Mfano: 'Es ist acht Uhr' (Ni saa mbili asubuhi / 8:00).",
            culturalTipSwahili = "Kushikilia muda (Pünktlichkeit) ni nguzo ya utamaduni wa Kijerumani. Chelewa dakika 5 utaonekana huna nidhamu!",
            isCompleted = false,
            isLocked = false,
            requiredPlan = "FREE"
        ),

        // ==================== LEVEL A2 (Basic Plan required) ====================
        LessonEntity(
            id = 6,
            level = "A2",
            lessonNumber = 1,
            titleGerman = "Einkaufen & Wochenmarkt",
            titleSwahili = "Kufanya Manunuzi Sokoni",
            swahiliIntro = "Somo hili linakufundisha msamiati wa kununua matunda, mboga, na nguo. Muhimu sana unapokuwa masomoni au kazini Ujerumani.",
            grammarExplanationSwahili = "Matumizi ya Akusatifu (Akkusativ) wakati wa manunuzi: 'der' inabadilika kuwa 'den' kwa vitu vya kiume unavyovinunua. Mfano: 'Ich kaufe den Apfel' (Nananua apeli).",
            culturalTipSwahili = "Unapofanya manunuzi supermaketi za Ujerumani kama ALDI au LIDL, beba mfuko wako wa nguo (Einkaufstasche). MIFUKO YA PLASTIKI HAIPIWI BURE!",
            isCompleted = false,
            isLocked = true,
            requiredPlan = "BASIC"
        ),
        LessonEntity(
            id = 7,
            level = "A2",
            lessonNumber = 2,
            titleGerman = "Wegbeschreibung & Orientierung",
            titleSwahili = "Kuuliza na Kuelekeza Njia",
            swahiliIntro = "Jinsi ya kuuliza vituo vya treni (Bahnhof), viwanja vya ndege, na mitaa mbalimbali Ujerumani.",
            grammarExplanationSwahili = "Matumizi ya vihusishi vya Datifu (Dativ Präpositionen): mit, nach, aus, zu, bei. Baada ya vihusishi hivi, majina hubadilika mfano: zu + der Kirche -> zur Kirche.",
            culturalTipSwahili = "Mfumo wa usafiri wa umma Ujerumani (S-Bahn, U-Bahn, Tram, Bus) unazingatia sana MTIKO WA MUDA (Pünktlichkeit). Treni ikisema saa 08:02, itaondoka saa 08:02 kamili!",
            isCompleted = false,
            isLocked = true,
            requiredPlan = "BASIC"
        ),
        LessonEntity(
            id = 8,
            level = "A2",
            lessonNumber = 3,
            titleGerman = "Wohnen & Möbel",
            titleSwahili = "Nyumba na Samani",
            swahiliIntro = "Kueleza nyumba yako, vyumba, na kupanga samani za nyumbani kwa Kijerumani.",
            grammarExplanationSwahili = "Matumizi ya Wechselpräpositionen (vihusishi vinavyobadilika kati ya Akkusativ na Dativ): in, an, auf, unter, über.",
            culturalTipSwahili = "Pango la nyumba Ujerumani hutofautisha Kaltmiete (pango bila huduma) na Warmmiete (pango linalojumuisha maji na usafi).",
            isCompleted = false,
            isLocked = true,
            requiredPlan = "BASIC"
        ),

        // ==================== LEVEL B1 (Standard Plan required) ====================
        LessonEntity(
            id = 9,
            level = "B1",
            lessonNumber = 1,
            titleGerman = "Beruf & Vorstellungsgespräch",
            titleSwahili = "Kazi na Usahili wa Kazi (Interview)",
            swahiliIntro = "Tayarisha maombi yako ya kazi na usahili wa kazi nchini Ujerumani au katika mashirika ya Kijerumani nchini Tanzania (mfano GIZ, Goethe-Institut).",
            grammarExplanationSwahili = "Matumizi ya Konjunktiv II kwa ajili ya kuomba nafasi kwa adabu: 'Ich würde gerne erfahren...', 'Könnten Sie mir bitte mitteilen...'. Mfumo huu ni nguzo ya mawasiliano ya kiofisi.",
            culturalTipSwahili = "Ujerumani, barua ya maombi ya kazi (Anschreiben) na Wasifu wa Kazi (Lebenslauf / CV) vina muundo rasmi wa kufuata uliowekwa kiviwango (Tabellarischer Lebenslauf).",
            isCompleted = false,
            isLocked = true,
            requiredPlan = "STANDARD"
        ),
        LessonEntity(
            id = 10,
            level = "B1",
            lessonNumber = 2,
            titleGerman = "Medien, Kultur & Umwelt",
            titleSwahili = "Vyombo vya Habari, Utamaduni na Mazingira",
            swahiliIntro = "Mada za kijamii kama utunzaji wa mazingira (Umweltschutz), urejelezaji wa taka (Mülltrennung), na usomaji wa magazeti ya Kijerumani.",
            grammarExplanationSwahili = "Passive Voice (Passiv mit werden): 'Das Gesetz wird beschlossen' (Sheria inapitishwa). Inatumika sana katika ripoti za habari na makala za kiofisi.",
            culturalTipSwahili = "Ujerumani kuna mfumo mkali wa kutenganisha taka nyumbani: Bio (taka za jikoni), Papier (karatasi), Plastik/Gelber Sack, na Restmüll (taka nyinginezo). Utunzaji wa mazingira ni sehemu ya maisha ya kila siku.",
            isCompleted = false,
            isLocked = true,
            requiredPlan = "STANDARD"
        ),
        LessonEntity(
            id = 11,
            level = "B1",
            lessonNumber = 3,
            titleGerman = "Gesundheit & Medizin",
            titleSwahili = "Afya na Tiba",
            swahiliIntro = "Eleza dalili za ugonjwa, zungumza na daktari, na nunua dawa kwenye duka la dawa (Apotheke).",
            grammarExplanationSwahili = "Matumizi ya Reflexive Verben (Vitenzi vinavyojirejea): 'sich fühlen' (kujisikia), 'sich erholen' (kupumzika/kupona). Mfano: Ich fühle mich krank.",
            culturalTipSwahili = "Duka la dawa Ujerumani linaitwa Apotheke na hutambuliwa kwa herufi kubwa nyekundu 'A'. Duka la vipodozi na usafi linaitwa Drogerie.",
            isCompleted = false,
            isLocked = true,
            requiredPlan = "STANDARD"
        ),

        // ==================== LEVEL B2 (Premium Plan required) ====================
        LessonEntity(
            id = 12,
            level = "B2",
            lessonNumber = 1,
            titleGerman = "Wirtschaft, Diplomatie & Wissenschaft",
            titleSwahili = "Uchumi, Diplomasia na Sayansi",
            swahiliIntro = "Somo la kiwango cha juu kwa ajili ya wataalamu, wanadiplomasia, na wanafunzi wanaojiandaa na masomo ya Shahada za Juu (Master/PhD) Ujerumani.",
            grammarExplanationSwahili = "Nomen-Verb-Verbindungen (Viunganishi vya Jina na Kitenzi): 'Einfluss nehmen auf' (Kuwepo na athari juu ya), 'In Betracht ziehen' (Kufikiria/Kutilia maanani). Miundo hii inaboresha sana uandishi wa kiakademia.",
            culturalTipSwahili = "Uhusiano wa kidiplomasia kati ya Tanzania na Ujerumani una historia ndefu kuanzia mambo ya utamaduni, utafiti wa kisayansi, na ushirikiano wa kiuchumi.",
            isCompleted = false,
            isLocked = true,
            requiredPlan = "PREMIUM"
        ),
        LessonEntity(
            id = 13,
            level = "B2",
            lessonNumber = 2,
            titleGerman = "Politik, Gesellschaft & Integration",
            titleSwahili = "Siasa, Jamii na Utangamano",
            swahiliIntro = "Zungumzia miundo ya siasa ya Ujerumani (Bundesrat, Bundestag) na changamoto za kijamii.",
            grammarExplanationSwahili = "Konjunktiv I kwa ajili ya kuripoti kauli za wengine (Indirekte Rede): 'Er sagte, er habe keine Zeit.'",
            culturalTipSwahili = "Ujerumani ni nchi yenye shirikisho la majimbo 16 (Bundesländer), kila jimbo likiwa na serikali na mfumo wake wa elimu.",
            isCompleted = false,
            isLocked = true,
            requiredPlan = "PREMIUM"
        )
    )

    fun getVocabForLesson(lessonId: Int): List<VocabItem> {
        return when (lessonId) {
            1 -> listOf(
                VocabItem("Guten Tag", "Habari za mchana / Siku njema", "Guu-ten Taag", "Guten Tag, wie geht es Ihnen?", "Habari za mchana, habari yako?"),
                VocabItem("Guten Morgen", "Habari za asubuhi", "Guu-ten Mor-gen", "Guten Morgen, Frau Müller!", "Habari za asubuhi, Bi. Müller!"),
                VocabItem("Guten Abend", "Habari za jioni", "Guu-ten Aaa-bend", "Guten Abend zusammen!", "Habari za jioni nanyi nyote!"),
                VocabItem("Hallo", "Jambo / Mambo", "Ha-lo", "Hallo Juma, wie geht's?", "Mambo Juma, inakuwaje?"),
                VocabItem("Tschüss", "Kwaheri (ya kishikaji)", "Chuus", "Tschüss, bis morgen!", "Kwaheri, tutaonana kesho!"),
                VocabItem("Auf Wiedersehen", "Kwaheri ya kuonana tena (rasmi)", "Auf Vii-der-zee-en", "Auf Wiedersehen, Herr Schmidt.", "Kwaheri ya kuonana tena, Bwana Schmidt."),
                VocabItem("Ich heiße...", "Jina langu ni / Naitwa...", "Ikh hai-se", "Ich heiße Neema.", "Naitwa Neema."),
                VocabItem("Wie heißen Sie?", "Jina lako nani? (Rasmi)", "Vii hai-sen Zii?", "Wie heißen Sie, bitte?", "Jina lako nani, tafadhali?"),
                VocabItem("Danke schön", "Asante sana", "Dan-ke shoen", "Vielen Dank für Ihre Hilfe!", "Asante sana kwa msaada wako!"),
                VocabItem("Bitte sehr", "Karibu sana / Naomba", "Bi-te zeer", "Bitte sehr, kein Problem!", "Karibu sana, hakuna shida!")
            )
            2 -> listOf(
                VocabItem("Eins", "Moja (1)", "Ains", "Ich habe eins.", "Nina moja."),
                VocabItem("Zwei", "Mbili (2)", "Tsvai", "Zwei Tassen Kaffee, bitte.", "Tasa mbili za kahawa, tafadhali."),
                VocabItem("Drei", "Tatu (3)", "Drai", "Drei Tage in Berlin.", "Siku tatu Berlin."),
                VocabItem("Vier", "Nne (4)", "Fiir", "Vier Personen.", "Watu nne."),
                VocabItem("Fünf", "Tano (5)", "Fuunf", "Fünf Euro.", "Euro tano."),
                VocabItem("Zehn", "Kumi (10)", "Tseen", "Zehn Minuten.", "Dakika kumi."),
                VocabItem("Zwanzig", "Ishirini (20)", "Tsvan-tsikh", "Zwanzig Schilling.", "Shilingi ishirini."),
                VocabItem("Einundzwanzig", "Ishirini na moja (21)", "Ain-und-tsvan-tsikh", "Einundzwanzig Jahre alt.", "Mwenye umri wa miaka 21."),
                VocabItem("Hundert", "Mia moja (100)", "Hun-dert", "Hundert Euro.", "Euro mia moja."),
                VocabItem("Wie viel kostet das?", "Hii inagharimu kiasi gani?", "Vii fiil kos-tet das?", "Wie viel kostet das Brot?", "Aina hii ya mkate inagharimu kiasi gani?")
            )
            3 -> listOf(
                VocabItem("Der Kaffee", "Kahawa", "Der Ka-fe", "Ich möchte einen Kaffee.", "Ningependa kahawa moja."),
                VocabItem("Der Tee", "Chai", "Der Tee", "Möchten Sie Tee?", "Je, ungependa chai?"),
                VocabItem("Das Wasser", "Maji", "Das Va-ser", "Ein Glas Wasser, bitte.", "Glasi moja ya maji, tafadhali."),
                VocabItem("Das Brot", "Mkate", "Das Broot", "Frisches Brot.", "Mkate mpya/mbichi."),
                VocabItem("Die Speisekarte", "Orodha ya chakula (Menu)", "Dii Shpai-se-kar-te", "Die Speisekarte, bitte.", "Menu ya chakula, tafadhali."),
                VocabItem("Die Rechnung", "Bili", "Dii Rekh-nung", "Zahlen, bitte! Die Rechnung.", "Kulipia tafadhali! Lete bili."),
                VocabItem("Lecker", "Tamu sana", "Le-ker", "Das Essen ist sehr lecker!", "Chakula hiki ni tamu sana!"),
                VocabItem("Guten Appetit!", "Mlo mwema / Karibu chakula!", "Guu-ten A-pe-tiit!", "Guten Appetit zusammen!", "Mlo mwema nyote!")
            )
            4 -> listOf(
                VocabItem("Der Vater", "Baba", "Der Faa-ter", "Mein Vater heißt Hassan.", "Baba angu anaitwa Hassan."),
                VocabItem("Die Mutter", "Mama", "Dii Muu-ter", "Meine Mutter kocht gut.", "Mama angu anapika vizuri."),
                VocabItem("Das Kind", "Mtoto", "Das Kind", "Das Kind spielt.", "Mtoto anacheza."),
                VocabItem("Der Bruder", "Kaka / Ndugu wa kiume", "Der Bruu-der", "Mein Bruder wohnt in Arusha.", "Kaka angu anakaa Arusha."),
                VocabItem("Die Schwester", "Dada / Ndugu wa kike", "Dii Shves-ter", "Meine Schwester studiert.", "Dada angu anasoma.")
            )
            5 -> listOf(
                VocabItem("Die Uhr", "Saa", "Dii Uur", "Wie viel Uhr ist es?", "Ni saa ngapi?"),
                VocabItem("Es ist acht Uhr", "Ni saa mbili (8:00)", "Es ist akht uur", "Es ist acht Uhr morgens.", "Ni saa mbili asubuhi."),
                VocabItem("Der Tag", "Siku", "Der Taag", "Einen schönen Tag noch!", "Siku njema!")
            )
            6 -> listOf(
                VocabItem("Einkaufen", "Kufanya manunuzi", "Ain-kau-fen", "Ich gehe einkaufen.", "Nenda kufanya manunuzi."),
                VocabItem("Der Apfel", "Tunda la apeli", "Der Ap-fel", "Ein roter Apfel.", "Apeli jekundu."),
                VocabItem("Der Markt", "Soko", "Der Markt", "Der Wochenmarkt ist heute.", "Soko la wiki liko leo.")
            )
            7 -> listOf(
                VocabItem("Der Bahnhof", "Kituo cha treni", "Der Baan-hoof", "Wo ist der Bahnhof?", "Kituo cha treni kiko wapi?"),
                VocabItem("Links", "Kushoto", "Links", "Biegen Sie links ab.", "Kata kushoto."),
                VocabItem("Rechts", "Kulia", "Rekhts", "Gehen Sie nach rechts.", "Nenda kulia.")
            )
            8 -> listOf(
                VocabItem("Die Wohnung", "Nyumba / Fleti", "Dii Voo-nung", "Meine Wohnung ist groß.", "Nyumba yangu ni kubwa."),
                VocabItem("Das Zimmer", "Chumba", "Das Tsi-mer", "Drei Zimmer.", "Vyumba vitatu."),
                VocabItem("Der Tisch", "Meza", "Der Tish", "Ein Tisch aus Holz.", "Meza ya mbao.")
            )
            9 -> listOf(
                VocabItem("Der Beruf", "Kazi / Taaluma", "Der Be-ruuf", "Was sind Sie von Beruf?", "Kazi yako ni ipi?"),
                VocabItem("Das Vorstellungsgespräch", "Usahili wa kazi (Interview)", "Das For-shtel-ungs-ge-shpraekh", "Morgen habe ich ein Vorstellungsgespräch.", "Kesho nina usahili wa kazi."),
                VocabItem("Der Lebenslauf", "Wasifu wa kazi (CV)", "Der Lee-bens-lauf", "Hier ist mein Lebenslauf.", "Huu hapa wasifu wangu wa kazi.")
            )
            10 -> listOf(
                VocabItem("Die Umwelt", "Mazingira", "Dii Um-velt", "Wir müssen die Umwelt schützen.", "Lazima tulinde mazingira."),
                VocabItem("Die Zeitung", "Gazeti", "Dii Tsai-tung", "Ich lese die Zeitung.", "Ninasoma gazeti."),
                VocabItem("Müll trennen", "Kutenganisha taka", "Muul tre-nen", "In Deutschland muss man Müll trennen.", "Ujerumani lazima utenganishe taka.")
            )
            else -> listOf(
                VocabItem("Lernen", "Kujifunza", "Ler-nen", "Ich lerne Deutsch.", "Jifunze Kijerumani."),
                VocabItem("Sprechen", "Kuzungumza", "Shpre-khen", "Sprechen Sie Swahili?", "Je, unazungumza Kiswahili?"),
                VocabItem("Tanzania", "Tanzania", "Tan-za-ni-a", "Ich komme aus Tansania.", "Ninatoka Tanzania."),
                VocabItem("Deutschland", "Ujerumani", "Doich-land", "Deutschland ist schön.", "Ujerumani ni nzuri.")
            )
        }
    }

    fun getQuizForLesson(lessonId: Int): List<QuizQuestion> {
        return when (lessonId) {
            1 -> listOf(
                QuizQuestion(
                    questionSwahili = "Je, ni ipi salamu sahihi ya Kijerumani unayosema kabla ya saa 5 asubuhi?",
                    options = listOf("Guten Abend", "Guten Morgen", "Guten Tag", "Gute Nacht"),
                    correctAnswerIndex = 1,
                    explanationSwahili = "'Guten Morgen' maana yake ni 'Habari za asubuhi'. Inatumika asubuhi kuanzia macheo hadi saa tano."
                ),
                QuizQuestion(
                    questionSwahili = "Unataka kusema 'Naitwa Juma' kwa Kijerumani. Je, ni sentensi ipi sahihi?",
                    options = listOf("Ich heiße Juma", "Du heißt Juma", "Sie heißen Juma", "Er heißt Juma"),
                    correctAnswerIndex = 0,
                    explanationSwahili = "'Ich heiße...' inatumika kwa nafsi ya kwanza (Mimi/Naitwa...). 'Du heißt' maana yake unaitwa."
                ),
                QuizQuestion(
                    questionSwahili = "Je, neno 'Danke schön' lina maana gani kwa Kiswahili?",
                    options = listOf("Karibu sana", "Samahani", "Asante sana", "Kwaheri"),
                    correctAnswerIndex = 2,
                    explanationSwahili = "'Danke schön' au 'Vielen Dank' inamaanisha 'Asante sana'."
                ),
                QuizQuestion(
                    questionSwahili = "Ukimkuta Mjerumani mtu mzima usiyemjua, utatumia neno gani kuonyesha heshima?",
                    options = listOf("Du", "Sie", "Er", "Wir"),
                    correctAnswerIndex = 1,
                    explanationSwahili = "'Sie' (kwa herufi kubwa) inatumika kwa heshima kwa watu wazima, viongozi, au wageni."
                )
            )
            2 -> listOf(
                QuizQuestion(
                    questionSwahili = "Je, namba 'einundzwanzig' (21) inaundwa vipi kwa mantiki ya Kijerumani?",
                    options = listOf("20 + 1 (Zwanzigeins)", "1 + na + 20 (Ein + und + zwanzig)", "10 + 11 (Zehneilf)", "2 + 10 (Zweizehn)"),
                    correctAnswerIndex = 1,
                    explanationSwahili = "Kijerumani kinasoma mmoja kabla ya mbao: 'ein' (1) + 'und' (na) + 'zwanzig' (20) = 21."
                ),
                QuizQuestion(
                    questionSwahili = "Unataka kuuliza 'Hii inagharimu kiasi gani?'. Utasemaje?",
                    options = listOf("Wo ist das?", "Wie viel kostet das?", "Wie geht es dir?", "Wie heißen Sie?"),
                    correctAnswerIndex = 1,
                    explanationSwahili = "'Wie viel kostet das?' inamaanisha 'Hii inagharimu kiasi gani?' au 'Bei gani?'."
                )
            )
            3 -> listOf(
                QuizQuestion(
                    questionSwahili = "Je, 'Ich möchte Kaffee' maana yake ni nini kwa Kiswahili?",
                    options = listOf("Sitaki kahawa", "Ningependa kahawa", "Kahawa ni mbaya", "Kesho nitakunywa kahawa"),
                    correctAnswerIndex = 1,
                    explanationSwahili = "'Ich möchte...' inamaanisha 'Ningependa...' - lugha ya adabu na heshima."
                )
            )
            else -> listOf(
                QuizQuestion(
                    questionSwahili = "Je, 'Ich lerne Deutsch' inamaanisha nini?",
                    options = listOf("Ninasoma Kijerumani", "Ninazungumza Kiswahili", "Ninakwenda Ujerumani", "Mimi ni Mjerumani"),
                    correctAnswerIndex = 0,
                    explanationSwahili = "'Ich lerne Deutsch' inamaanisha 'Ninasoma/Jifunze Kijerumani'."
                )
            )
        }
    }
}
