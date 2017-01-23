package sqlParser.generators;

import com.avaje.ebeaninternal.server.lib.util.Str;
import models.ColumnDef;
import models.SchemaDef;
import models.TableDef;
import models.ExtensionDef;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author carl
 */
public class ExtensionMaker {
    private final Long      seed;
    private final SchemaDef schemaDef;
    private final Random    rand;
    private final Integer   idOffset;
    private final Integer   minEntities;
    private final Integer   maxEntities;
    private final Integer   nullChance;

    String[] domain = {"tu-bs.de","aol.com","att.net","comcast.net","facebook.com","gmail.com","gmx.com","googlemail.com","google.com","hotmail.com","hotmail.co.uk","mac.com","me.com","mail.com","msn.com","live.com","sbcglobal.net","verizon.net","yahoo.com","yahoo.co.uk"};
    String[] metal = {"Calcium","Strontium","Barium","Radium","Aluminum","Gallium","Indium","Tin","Thallium","Lead","Bismuth","Scandium","Titanium","Vanadium","Chromium","Manganese","Iron","Cobalt","Nickel","Copper","Zinc","Yttrium","Zirconium","Niobium","Molybdenum","Technetium","Ruthenium","Rhodium","Palladium","Silver","Cadmium","Lanthanum","Hafnium","Tantalum","Tungsten","Rhenium","Osmium","Iridium","Platinum","Gold"};
    String[] title = {"1984","A Doll_s House","A Sentimental Education","Absalom, Absalom!","The Adventures of Huckleberry Finn","The Aeneid","Anna Karenina","Beloved","Berlin Alexanderplatz","Blindness","The Book of Disquiet","The Book of Jo","The Brothers Karamazov","Buddenbrooks","Canterbury Tales","The Castle","Children of Gebelawi","Collected Fictions","Complete Poems","The Complete Stories","The Complete Tales","Confessions of Zeno","Crime and Punishment","Dead Souls","The Death of Ivan Ilyich and Other Stories","Decameron","The Devil to Pay in the Backlands","Diary of a Madman and Other Stories","The Divine Comedy","Don Quixote","Essays","Fairy Tales and Stories","Faust","Gargantua and Pantagruel","The Golden Notebook","Great Expectations","Gulliver_s Travels","Gypsy Ballads","Hamlet","History","Hunger","The Idiot","The Iliad","Independent People","Invisible Man","Jacques the Fatalist and His Master","Journey to the End of the Night","King Lear","Leaves of Grass","The Life and Opinions of Tristram Shandy","Lolita","Love in the Time of Cholera","Madame Bovary","The Magic Mountain","Mahabharat","The Man Without Qualities","The Mathnaw","Medea","Memoirs of Hadrian","Metamorphoses","Middlemarch","Midnight_s Children","Moby-Dick","Mrs. Dalloway","Njaals Sag","Nostromo","The Odyssey","Oedipus the King Sophocle","Old Goriot","The Old Man and the Sea","One Hundred Years of Solitude","The Orchard","Othello","Pedro Paramo","Pippi Longstocking","Poems","The Possessed","Pride and Prejudice","The Ramayana","The Recognition of Sakuntala","The Red and the Black","Remembrance of Things Past","Season of Migration to the North","Sons and Lovers","The Sound and the Fury","The Sound of the Mountain","The Stranger","The Tale of Genji","Things Fall Apart","Thousand and One Night","The Tin Drum","To the Lighthouse","The Trial","Trilogy: Molloy, Malone Dies, The Unnamable","Ulysses","War and Peace","Wuthering Heights","Zorba the Greek"};
    String[] firstname = {"Fabio","Sören","Nargiz","Abigail","Alexandra","Alison","Amanda","Amelia","Amy","Andrea","Angela","Anna","Anne","Audrey","Ava","Bella","Bernadette","Carol","Caroline","Carolyn","Chloe","Claire","Deirdre","Diana","Diane","Donna","Dorothy","Elizabeth","Ella","Emily","Emma","Faith","Felicity","Fiona","Gabrielle","Grace","Hannah","Heather","Irene","Jan","Jane","Jasmine","Jennifer","Jessica","Joan","Joanne","Julia","Karen","Katherine","Kimberly","Kylie","Lauren","Leah","Lillian","Lily","Lisa","Madeleine","Maria","Mary","Megan","Melanie","Michelle","Molly","Natalie","Nicola","Olivia","Penelope","Pippa","Rachel","Rebecca","Rose","Ruth","Sally","Samantha","Sarah","Sonia","Sophie","Stephanie","Sue","Theresa","Tracey","Una","Vanessa","Victoria","Virginia","Wanda","Wendy","Yvonne","Zoe","Adam","Adrian","Alan","Alexander","Andrew","Anthony","Austin","Benjamin","Blake","Boris","Brandon","Brian","Cameron","Carl","Charles","Christian","Christopher","Colin","Connor","Dan","David","Dominic","Dylan","Edward","Eric","Evan","Frank","Gavin","Gordon","Harry","Ian","Isaac","Jack","Jacob","Jake","James","Jason","Joe","John","Jonathan","Joseph","Joshua","Julian","Justin","Keith","Kevin","Leonard","Liam","Lucas","Luke","Matt","Max","Michael","Nathan","Neil","Nicholas","Oliver","Owen","Paul","Peter","Phil","Piers","Richard","Robert","Ryan","Sam","Sean","Sebastian","Simon","Stephen","Steven","Stewart","Thomas","Tim","Trevor","Victor","Warren","William"};
    String[] word = {"cheap","three","thoughtless","acceptable","yellow","eggnog","rely","exchange","abandoned","cause","order","pet","potato","grin","tired","earth","faithful","jog","lick","fold","innate","periodic","birds","dusty","resolute","unwieldy","stream","surprise","high","can","vanish","employ","poor","snobbish","swim","shrug","needless","measly","boy","extra-large","mammoth","puzzling","flag","need","economic","macabre","gainful","notebook","hug","note","industrious","argument","bustling","temporary","temper","sheet","loaf","silver","miss","cemetery","pop","plastic","sink","gratis","parsimonious","educated","marvelous","witty","hop","fierce","afford","abounding","birth","suit","tangy","basketball","guarantee","classy","drain","better","pleasure","groan","melted","part","carriage","satisfying","room","calculator","tan","inconclusive","women","tidy","tremble","cherry","confess","chunky","rot","handsome","sip","bless","rice","son","bitter","spotless","ink","complete","bushes","frame","toad","flock","moldy","likeable","worthless","panicky","cabbage","weary","call","kiss","sidewalk","ablaze","romantic","undress","unknown","pin","resonant","mend","lighten","turn","reflective","cactus","lean","girls","tail","steam","synonymous","crack","fax","cast","fertile","upset","action","telephone","pricey","theory","guess","rings","robin","basin","cruel","practise","sore","rich","capricious","tiny","jeans","wood","salty","record","lip","interest","school","annoying","screw","spot","pies","sleepy","guitar","competition","magnificent","march","well-groomed","elegant","adhesive","cycle","attend","harsh","trite","coat","door","tree","utter","spell","crayon","brown","quarter","judge","necessary","mark","difficult","scrawny","interrupt","dry","table","venomous","acidic","melodic","mitten","cakes","flippant","scatter"};
    String[] animal = {"Dog","Squirrel","Cougar","Raven","Cat","Alligator","Polar Bear","Possum","Lion","Crocodile","Bear","Koala","Tiger","Chipmunks","Grizzly","Swan","Rabbit","Whale","Platypus","Goat","Duck","Octopus","Sheep","Zebra","Anaconda","Cow","Goose","Eagle","Pig","Donkey","Osprey","Parrot","Panda","Poodle","Elephant","Skunk","Bull","Dolphin","Jaguar","Rooster","Salamander","Tortoise","Chicken","Raccoon","Hedgehog","Piranha","Penguin","Iguana","Turkey","Polar Bear","Flamingo","Crow","Walrus","Worms","Ape","Jellyﬁsh","Frog","Monkey","Giraffe","Sloth","Pigeon","Ostrich","Lobster","Wolf","Swordﬁsh","Cod","Deer","Mackerel","Gecko","Antelope","Bonito","Spider","Catﬁsh","Trout","Cobra","Whale","Falcon","Hamster","Fox","Chimpanzee","Seal","Lama","Kangaroo","Leopard","Camel","Porcupines","Cheetah","Gorilla","Hyena","Crab","Toad","Hippo","Mouse","Quail","Rhino","Rat","Moose","Crab","Tuna","Manta"};
    String[] city = {"New York City","Los Angeles","Chicago","Houston","Philadelphia","Phoenix","San Antonio","San Diego","Dallas","San Jose","Austin","Jacksonville","Indianapolis","San Francisco","Columbus","Fort Worth","Charlotte","Detroit","El Paso","Memphis","Boston","Seattle","Denver","Washington","Nashville-Davidson","Baltimore","Louisville/Jefferson","Portland","Oklahoma","Milwaukee","Las Vegas","Albuquerque","Tucson","Fresno","Sacramento","Long Beach","Kansas","Mesa","Virginia Beach","Atlanta","Colorado Springs","Raleigh","Omaha","Miami","Oakland","Tulsa","Minneapolis","Cleveland","Wichita","Arlington","New Orleans","Bakersfield","Tampa","Honolulu","Anaheim","Aurora","Santa Ana","St. Louis","Riverside","Corpus Christi","Pittsburgh","Lexington-Fayette","Anchorage","Stockton","Cincinnati","Buffalo","Orlando"};
    String[] lastname = {"Naczk","Friebe","Medina","Alizada","Arnold","Chase","Blake","Blackburn","Chan","Adkins","Pacheco","Barry","Jordan","Bowers","Pitts","Duarte","Dean","Wilkinson","Mcintyre","Joseph","Melton","Summers","Mcintosh","Moody","Pollard","Tran","Delacruz","Cox","Tucker","Wilkerson","Lindsey","Boone","Goodwin","Doyle","Hanson","Velazquez","Camacho","Moss","Tanner","Clark","Bautista","Glenn","Mccormick","Blackwell","Orozco","Clay","Perez","Cervantes","Vance","Lynn","Berry","Perkins","Cherry","Mejia","Rocha","Rosales","Simmons","Calhoun","Mcconnell","Mckee","Davis","Horne","Skinner","Galloway","Cardenas","Villa","Woods","Arroyo","Galvan","Castillo","Sanders","Flowers","Barr","Montes","Briggs","Maynard","Bradshaw","Higgins","Hart","Duke","Alvarez","Hill","Davenport","Douglas","Olsen","Noble","Mason","Parks","Allison","Braun","Bean","Curry","Cummings","Kent","Dodson","Best","Jacobson","Guzman","Hale","Mccarty","Meza","Maxwell","Mcneil","Abraham","Allan","Alsop","Anderson","Arnold","Avery","Bailey","Baker","Ball","Bell","Berry","Black","Blake","Bond","Bower","Brown","Buckland","Burgess","Butler","Cameron","Campbell","Carr","Chapman","Churchill","Clark","Clarkson","Coleman","Cornish","Davidson","Davies","Dickens","Dowd","Duncan","Dyer","Edmunds","Ellison","Ferguson","Fisher","Forsyth","Fraser","Gibson","Gill","Glover","Graham","Grant","Gray","Greene","Hamilton","Hardacre","Harris","Hart","Hemmings","Henderson","Hill","Hodges","Howard","Hudson","Hughes","Hunter","Ince","Jackson","James","Johnston","Jones","Kelly","Kerr","King","Knox","Lambert","Langdon","Lawrence","Lee","Lewis","Lyman","MacDonald","Mackay","Mackenzie","MacLeod","Manning","Marshall","Martin","Mathis","May","McDonald","McLean","McGrath","Metcalfe","Miller","Mills","Mitchell","Morgan","Morrison","Murray","Nash","Newman","Nolan","North","Ogden","Oliver","Paige","Parr","Parsons","Paterson","Payne","Peake","Peters","Piper","Poole","Powell","Pullman","Quinn","Rampling","Randall","Rees","Reid","Roberts","Robertson","Ross","Russell","Rutherford","Sanderson","Scott","Sharp","Short","Simpson","Skinner","Slater","Smith","Springer","Stewart","Sutherland","Taylor","Terry","Thomson","Tucker","Turner","Underwood","Vance","Vaughan","Walker","Wallace","Walsh","Watson","Welch","White","Wilkins","Wilson","Wright","Young"};
    String[] colour = {"IndianRed","LightCoral","Salmon","DarkSalmon","LightSalmon","Crimson","Red","FireBrick","DarkRed","Pink","LightPink","HotPink","DeepPink","MediumVioletRed","PaleVioletRed","LightSalmon","Coral","Tomato","OrangeRed","DarkOrange","Orange","Gold","Yellow","LightYellow","LemonChiffon","LightGoldenRodYellow","PapayaWhip","Moccasin","PeachPuff","PaleGoldenrod","Khaki","DarkKhaki","Lavender","Thistle","Plum","Violet","Orchid","Fuchsia","Magenta","MediumOrchid","MediumPurple","BlueViolet","DarkViolet","DarkOrchid","DarkMagenta","Purple","Indigo","SlateBlue","DarkSlateBlue","MediumSlateBlue","GreenYellow","Chartreuse","LawnGreen","Lime","LimeGreen","PaleGreen","LightGreen","MediumSpringGreen","SpringGreen","MediumSeaGreen","SeaGreen","ForestGreen","Green","DarkGreen","YellowGreen","OliveDrab","Olive","DarkOliveGreen","MediumAquamarine","DarkSeaGreen","LightSeaGreen","DarkCyan","Teal","Aqua","Cyan","LightCyan","PaleTurquoise","Aquamarine","Turquoise","MediumTurquoise","DarkTurquoise","CadetBlue","SteelBlue","LightSteelBlue","PowderBlue","LightBlue","SkyBlue","LightSkyBlue","DeepSkyBlue","DodgerBlue","CornflowerBlue","MediumSlateBlue","RoyalBlue","Blue","MediumBlue","DarkBlue","Navy","MidnightBlue","Cornsilk","BlanchedAlmond","Bisque","NavajoWhite","Wheat","BurlyWood","Tan","RosyBrown","SandyBrown","Goldenrod","DarkGoldenrod","Peru","Chocolate","SaddleBrown","Sienna","Brown","Maroon","White","Snow","Honeydew","MintCream","Azure","AliceBlue","GhostWhite","WhiteSmoke","Seashell","Beige","OldLace","FloralWhite","Ivory","AntiqueWhite","Linen","LavenderBlush","MistyRose","Gainsboro","LightGray","Silver","DarkGray","Gray","DimGray","LightSlateGray","SlateGray","DarkSlateGray","Black"};
    String[] country = {"Qatar","Luxembourg","Singapore","Brunei Darussalam","Kuwait","Norway","United Arab Emirates","Hong Kong","United States","Switzerland","Saudi Arabia","Bahrain","Netherlands","Ireland","Australia","Austria","Sweden","Germany","Taiwan","Canada","Denmark","Oman","Iceland","Belgium","France","Finland","United Kingdom","Japan","Republic Of Korea","New Zealand","Italy","Spain","Israel","Malta","Trinidad And Tobago","Slovenia","Equatorial Guinea","Czech Republic","Slovak Republic","Cyprus","Lithuania","Estonia","Portugal","Greece","Malaysia","Bahamas","Poland","Seychelles","Hungary","Kazakhstan","Russian Federation","Latvia","Chile","Antigua And Barbuda","Gabon","Argentina","Panama","Uruguay","Saint Kitts And Nevis","Croatia","Romania","Turkey","Libyan Arab Jamahiriya","Azerbaijan","Belarus","Mauritius","Mexico","Lebanon","Bulgaria","Bolivarian Republic Of Venezuela","Suriname","Iran","Botswana","Barbados","Montenegro","Palau","Turkmenistan","Costa Rica","Brazil","Thailand","Algeria","Colombia","China","Macedonia","Iraq","Dominican Republic","South Africa","Maldives","Serbia","Peru","Jordan","Grenada","Tunisia","Ecuador","Albania","Egypt","Saint Lucia","Saint Vincent And The Grenadines","Namibia","Sri Lanka","Mongolia","Dominica","Indonesia","Bosnia And Herzegovina","Jamaica","Paraguay","Angola","Fiji","Ukraine","Belize","El Salvador","Bhutan","Georgia","Morocco","Swaziland","Armenia","Guatemala","Philippines","Timor Leste","Guyana","Rep.Congo","Cape Verde","Bolivia","Nigeria","India","Viet Nam","Uzbekistan","Lao People S Democratic Republic","Samoa","Tonga","Myanmar","Moldova","Nicaragua","Pakistan","Honduras","Sudan","Zambia","Ghana","Yemen","Bangladesh","Mauritania","Kyrgyz Republic","Cambodia","Sao Tome And Principe","Kenya","Marshall Islands","Micronesia","Djibouti","Cameroon","Lesotho","Côte D Ivoir"};
    String[] syllable = {"an","au","be","ch","da","de","di","ei","el","en","er","es","ge","he","ht","ic","ie","in","it","le","li","nd","ne","ng","re","sc","se","si","st","te","un","en","er","ch","de","ei","ie","in","te","ge","un","en","er","sch","de","ei","ie","in","wi","te","ge","un","wo","ko","ir","re","ni","il"};
    String[] position = {"C","G","T","QB","RB","WR","TE","DT","DE","MLB","OLB","CB","S","K","H","LS","P","KOS","KR","PR"};
    String[] plant = {"Aloe Vera","Alfalfa","American Coffee Berry Tree","Bloodroot","Bouncing Bet","Bull Nettle","Bracken or Brake Fern","Burning Bush","Buttercup","Carelessweed ","Castor Bean","Chrysanthemums","Clover","Cocklebur","Creeping Charlie","Crown of Thorns","Curly Dock","Daffodil","Daphne","Delphinium","Devil_s Trumpet","Dogbane","Dutchman_s Breeches","Elderberry","Ergot","Fern","Fireweed","Foxglove","Poison Hemlock","Water Hemlock","Hemp","Horse Chestnut, Buckeyes","Horse Nettle","Horsetails","Hyacinth","Hydrangea","English Ivy","Ground Ivy","Poison Ivy","Jack-in-the-Pulpit","Japanese Yew","Jerusalem Cherry","Jimson Weed","Kalanchoe","Kentucky Coffee Tree","Kentucky Mahagony Tree","Klamath Weed","Lamb_s Quarters","Lantana","Larkspur","Daylily","True Lily","Lily-of-the-Valley","Lupine","Mad Apple","Maple, Red","Mayapple","Milkweed","Mint","Mountain Laurel","Nicker Tree","Nightshade","Oleander","Ohio Buckeye","Philodendron","Pigweed","Poinsettia","Poke","Purple Mint","Redroot","Rhododendron","Rhubarb","Rosary Pea","Squirrelcorn","Staggerweed","St. Johnswort","Stinging Nettle","Stink Weed","Stump Tree","Sudan Grass","Summer Cypress","Thorn Apple","Tulip","White Snakeroot","Wild Onion","Yellow Sage"};
    String[] postStatus = {"question","answer"};
    String[] study = {"acarology","accidence","aceology","acology","acoustics","adenology","aedoeology","aerobiology","aerodonetics","aerodynamics","aerolithology","aerology","aeronautics","aerophilately","aerostatics","agonistics","agriology","agrobiology","agrology","agronomics","agrostology","alethiology","algedonics","algology","anaesthesiology","anaglyptics","anagraphy","anatomy","andragogy","anemology","angelology","angiology","anthropobiology","anthropology","aphnology","apiology","arachnology","archaeology","archelogy","archology","arctophily","areology","aretaics","aristology","arthrology","astacology","astheniology","astrogeology","astrology","astrometeorology","astronomy","astrophysics","astroseismology","atmology","audiology","autecology","autology","auxology","avionics","axiology","bacteriology","balneology","barodynamics","barology","batology","bibliology","bibliotics","bioecology","biology","biometrics","bionomics","botany","bromatology","brontology","bryology","cacogenics","caliology","calorifics","cambistry","campanology","carcinology","cardiology","caricology","carpology","cartography","cartophily","castrametation","catacoustics","catalactics","catechectics","cetology","chalcography","chalcotriptics","chaology","characterology","chemistry","chirocosmetics","chirography","chirology","chiropody","chorology","chrematistics","chronobiology","chrysology","ciselure","climatology","clinology","codicology","coleopterology","cometology","conchology","coprology","cosmetology","cosmology","craniology","criminology 	","cryptology","dermatology","diplomatics","ecology","economics 	","Egyptology","electrochemistry","embryology","emetology","floristry","fluviology","gastroenterology","gemmology","genealogy","genesiology","genethlialogy","gnomonics","hematology ","hydrobiology","iamatology","idiopsychology","kidology","larithmics","linguistics","magnanerie ","martyrology","mechanics","metallogeny","metapolitics","meteoritics","meteorology","metrics ","metrology","micrology ","molinology","musicology","myology","nautics","nematology","neonatology","neurology","nomology","nostology ","numismatics","oceanology","oenology","olfactology ","oncology","ontology","ophthalmology","optology","orchidology","ornithology","orology","orthoepy","osmology","otology","paedotrophy","palaeobiology","paleobotany","papyrology","paroemiology","pelology","phenology","photobiology","physiology","planetology","podology","potamology","prosody","psephology","pseudoptics","psychophysics","quinology","rhabdology","runology","schematonics","scripophily","selenology","semiotics","sindonology","spectrology","stasiology","stratigraphy","symptomatology","syntax","tegestology","teuthology","thermodynamics","timbrology","topology","toxicology","trophology","uranography","venereology","xylography","zooarchaeology","zoology","zoopathology","zoosemiotics","zymology","zymurg"};

    public ExtensionMaker(
            Long seed,
            SchemaDef schemaDef,
            Integer minEntities,
            Integer maxEntities ) {

        this.seed           = seed;
        this.schemaDef      = schemaDef;
        this.rand           = new Random(this.seed);
        this.idOffset       = 0;
        this.minEntities    = minEntities;
        this.maxEntities    = maxEntities;
        this.nullChance     = 2;
    }

    public List<String> buildStatements() {
        Map<String, List<Map<String, String>>>  extensionByTableMap = new LinkedHashMap<>();

        //goes through every given table
        for (TableDef tableDef : schemaDef.getTableDefList()) {
            List<ColumnDef>             columnDefList;
            ExtensionDef                staticExtension;
            List<Map<String, String>>   entityList;
            Integer                     entityCount;
            int                         staticExtensionSize;

            staticExtensionSize   = 0;
            columnDefList   = tableDef.getColumnDefList();
            staticExtension = tableDef.getExtensionDef();

            entityList      = new ArrayList<>();
            entityCount     = randomBetween(this.minEntities, this.maxEntities);

            if(staticExtension != null) {
                staticExtensionSize = staticExtension.getExtensionList().size();
                staticExtension.getExtensionList().forEach(entityList::add);
            }

            List<String> idList = new ArrayList<>();
            entityList.stream().forEach((temp) -> {
                idList.add(temp.get("id"));
            });

            //variables used for combined keys
            Long comp = columnDefList
                    .stream()
                    .filter(x -> x.getMetaValueSet() == ColumnDef.META_VALUE_SET_FOREIGN_KEY && x.isPrimary())
                    .count();


            int[] comMember     = new int[(int) (long) comp];
            boolean[][] com2    = new boolean[entityCount][entityCount];
            boolean[][][] com3  = new boolean[entityCount][entityCount][entityCount];

            //goes through row given
            for (int i = 0 ; i < entityCount; i++) {
                Map<String, String> entityMap = new LinkedHashMap<>();

                int comCount = 0;

                //goes through column and fills it whit an random attribute according to its MetaValueSet
                for (int j = 0; j < columnDefList.size(); j++) {
                    ColumnDef columnDef = columnDefList.get(j);
                    String value;

                    if (!columnDef.isNotNull() && chance(nullChance)) {
                        value = "NULL";
                    } else {
                        switch (columnDef.getMetaValueSet()) {
                            case ColumnDef.META_VALUE_SET_MAIL:
                                value = gen(word) + "@" + gen(domain);
                                break;
                            case ColumnDef.META_VALUE_SET_FIRSTNAME:
                                value = gen(firstname);
                                break;
                            case ColumnDef.META_VALUE_SET_FOREIGN_KEY:
                                int number = randomBetween((this.idOffset + staticExtensionSize), this.minEntities);
                                value = "" + number;
                                if (columnDef.isPrimary()) {
                                    comMember[comCount] = number - idOffset;
                                    comCount++;
                                    if (comCount == comp) {
                                        if (comp == 2) {
                                            if (!com2[comMember[0]][comMember[1]]) {
                                                com2[comMember[0]][comMember[1]] = true;
                                            } else {
                                                comCount--;
                                                value = null;
                                                j--;
                                            }
                                        }
                                        if (comp == 3) {
                                            if (!com3[comMember[0]][comMember[1]][comMember[2]]) {
                                                com3[comMember[0]][comMember[1]][comMember[2]] = true;
                                            } else {
                                                comCount--;
                                                value = null;
                                                j--;
                                            }
                                        }

                                    }
                                }
                                break;
                            case ColumnDef.META_VALUE_SET_ID:
                                while(idList.contains(""+i)){
                                    i++;
                                }
                                value = String.valueOf(idOffset  + i);
                                break;
                            case ColumnDef.META_VALUE_SET_ANIMAL:
                                value = gen(animal);
                                break;
                            case ColumnDef.META_VALUE_SET_CITY:
                                value = gen(city);
                                break;
                            case ColumnDef.META_VALUE_SET_STUDY:
                                value = gen(study);
                                break;
                            case ColumnDef.META_VALUE_SET_POSITION:
                                value = gen(position);
                                break;
                            case ColumnDef.META_VALUE_SET_TITLE:
                                value = gen(title);
                                break;
                            case ColumnDef.META_VALUE_SET_FULLNAME:
                                value = gen(firstname) + gen(lastname);
                                break;
                            case ColumnDef.META_VALUE_SET_METAL:
                                value = gen(metal);
                                break;
                            case ColumnDef.META_VALUE_SET_POSTTYPE:
                                value = gen(postStatus);
                                break;
                            case ColumnDef.META_VALUE_SET_DAY:
                                value = String.valueOf(random(1, 30));
                                break;
                            case ColumnDef.META_VALUE_SET_NAME:
                                value = gen(firstname);
                                break;
                            case ColumnDef.META_VALUE_SET_MONTH:
                                value = String.valueOf(random(1, 12));
                                break;
                            case ColumnDef.META_VALUE_SET_YEAR:
                                value = String.valueOf(random(1930, 88));
                                break;
                            case ColumnDef.META_VALUE_SET_LOREM_IPSUM:
                                value = gen(word) + gen(word);
                                break;
                            case ColumnDef.META_VALUE_SET_LASTNAME:
                                value = gen(lastname);
                                break;
                            case ColumnDef.META_VALUE_SET_COLOR:
                                value = gen(colour);
                                break;
                            case ColumnDef.META_VALUE_SET_DATE:
                                LocalDate date;
                                date = LocalDate.now();
                                if(columnDef.getMaxValueSet() == 0){
                                    date = date.minusDays(random(21390));
                                }else{
                                    int year = date.getYear();
                                    date = date.minusDays((year - columnDef.getMaxValueSet()) * 365);
                                    date = date.minusDays(random((columnDef.getMaxValueSet() - columnDef.getMinValueSet())*365));
                                }
                                value = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
                                break;
                            case ColumnDef.META_VALUE_SET_PLANT:
                                value = gen(plant);
                                break;
                            case ColumnDef.META_VALUE_SET_GRADE:
                                value =  String.valueOf(random(10, 41) / 10);
                                break;
                            case ColumnDef.META_VALUE_SET_LOCATION:
                                value = gen(country);
                                break;
                            default:
                                switch (columnDef.getDataType()) {
                                    case "INT":
                                    case "int":
                                        int min = columnDef.getMinValueSet();
                                        int max = columnDef.getMaxValueSet();
                                        if (max != 0) {
                                            value = String.valueOf(random(min, max - min));
                                        } else {
                                            value = String.valueOf(random(1111));
                                        }
                                        break;
                                    case "VARCHAR(255)":
                                    case "VARCHAR":
                                        value = wordGenerator();
                                        break;
                                    case "BOOLEAN":
                                    case "boolean":
                                        value = String.valueOf(rand.nextBoolean());
                                        break;
                                    default:
                                        value = String.valueOf(columnDef.getName() + columnDef.getDataType());
                                        break;
                                }
                                break;
                        }
                    }
                    entityMap.put(
                            columnDef.getName(),
                            value
                    );
                }

                entityList.add(entityMap);
            }
            extensionByTableMap.put(
                    tableDef.getName(),
                    entityList
            );
        }


        // InsertStatement Generator
        List<String> insertStatementList = new ArrayList<>();;
        extensionByTableMap.forEach((tableName, entityList) -> {
            if(entityList.size() > 0) {
                Set<String>     columnNames;
                String          insertTemplate;

                columnNames = entityList.get(0).keySet();

                insertTemplate = "INSERT INTO " + tableName + " ( "
                        + String.join(", ", columnNames) + " ) "
                        + "VALUES ( ";

                insertStatementList.addAll(
                        entityList
                            .stream()
                            .map(entity -> {
                                String insertStatement;
                                insertStatement = insertTemplate;

                                String values =
                                        String.join(
                                                ", ",
                                                entity
                                                    .values()
                                                    .stream()
                                                    .map(s ->
                                                            (s.equalsIgnoreCase("null")) ?
                                                                    s.toUpperCase() : "'" + s + "'")
                                                    .collect(Collectors.toList())
                                        );

                                return insertStatement + values + " );";
                            })
                            .collect(Collectors.toList()));
            }
        });

        return insertStatementList;
    }

    private Integer randomBetween(Integer minEntities, Integer maxEntities) {
        return ( minEntities + rand.nextInt(maxEntities - minEntities));
    }

    public int random( int min,int max){
        return ( min + rand.nextInt(max));
    }

    public int random(int max){
        return rand.nextInt(max);
    }

    public boolean chance(int chance){
        return random(100) < chance;
    }

    private String wordGenerator() {
        String  word;
        int     num;

        num = random(2, 4);
        word = "";

        while( num > 0 ) {
            word += gen(syllable);
            num--;
        }

        return word;
    }

    public String gen(String[] field){
        return "" + field[random(field.length)];
    }

    public Long getSeed() {
        return seed;
    }

    public SchemaDef getSchemaDef() {
        return schemaDef;
    }
}