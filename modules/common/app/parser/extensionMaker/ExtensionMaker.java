package parser.extensionMaker;

import models.ColumnDef;
import models.SchemaDef;
import models.TableDef;

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
    private final Integer   nullChance = 2;

    String[] mail = {"tu-bs.de","aol.com","att.net","comcast.net","facebook.com","gmail.com","gmx.com","googlemail.com","google.com","hotmail.com","hotmail.co.uk","mac.com","me.com","mail.com","msn.com","live.com","sbcglobal.net","verizon.net","yahoo.com","yahoo.co.uk"};
    String[] metal = {"Calcium","Strontium","Barium","Radium","Aluminum","Gallium","Indium","Tin","Thallium","Lead","Bismuth","Scandium","Titanium","Vanadium","Chromium","Manganese","Iron","Cobalt","Nickel","Copper","Zinc","Yttrium","Zirconium","Niobium","Molybdenum","Technetium","Ruthenium","Rhodium","Palladium","Silver","Cadmium","Lanthanum","Hafnium","Tantalum","Tungsten","Rhenium","Osmium","Iridium","Platinum","Gold"};
    String[] title = {"1984","A Doll's House","A Sentimental Education","Absalom, Absalom!","The Adventures of Huckleberry Finn","The Aeneid","Anna Karenina","Beloved","Berlin Alexanderplatz","Blindness","The Book of Disquiet","The Book of Jo","The Brothers Karamazov","Buddenbrooks","Canterbury Tales","The Castle","Children of Gebelawi","Collected Fictions","Complete Poems","The Complete Stories","The Complete Tales","Confessions of Zeno","Crime and Punishment","Dead Souls","The Death of Ivan Ilyich and Other Stories","Decameron","The Devil to Pay in the Backlands","Diary of a Madman and Other Stories","The Divine Comedy","Don Quixote","Essays","Fairy Tales and Stories","Faust","Gargantua and Pantagruel","The Golden Notebook","Great Expectations","Gulliver's Travels","Gypsy Ballads","Hamlet","History","Hunger","The Idiot","The Iliad","Independent People","Invisible Man","Jacques the Fatalist and His Master","Journey to the End of the Night","King Lear","Leaves of Grass","The Life and Opinions of Tristram Shandy","Lolita","Love in the Time of Cholera","Madame Bovary","The Magic Mountain","Mahabharat","The Man Without Qualities","The Mathnaw","Medea","Memoirs of Hadrian","Metamorphoses","Middlemarch","Midnight's Children","Moby-Dick","Mrs. Dalloway","Njaals Sag","Nostromo","The Odyssey","Oedipus the King Sophocle","Old Goriot","The Old Man and the Sea","One Hundred Years of Solitude","The Orchard","Othello","Pedro Paramo","Pippi Longstocking","Poems","The Possessed","Pride and Prejudice","The Ramayana","The Recognition of Sakuntala","The Red and the Black","Remembrance of Things Past","Season of Migration to the North","Sons and Lovers","The Sound and the Fury","The Sound of the Mountain","The Stranger","The Tale of Genji","Things Fall Apart","Thousand and One Night","The Tin Drum","To the Lighthouse","The Trial","Trilogy: Molloy, Malone Dies, The Unnamable","Ulysses","War and Peace","Wuthering Heights","Zorba the Greek"};
    String[] firstname = {"Fabio","Sören","Abigail","Alexandra","Alison","Amanda","Amelia","Amy","Andrea","Angela","Anna","Anne","Audrey","Ava","Bella","Bernadette","Carol","Caroline","Carolyn","Chloe","Claire","Deirdre","Diana","Diane","Donna","Dorothy","Elizabeth","Ella","Emily","Emma","Faith","Felicity","Fiona","Gabrielle","Grace","Hannah","Heather","Irene","Jan","Jane","Jasmine","Jennifer","Jessica","Joan","Joanne","Julia","Karen","Katherine","Kimberly","Kylie","Lauren","Leah","Lillian","Lily","Lisa","Madeleine","Maria","Mary","Megan","Melanie","Michelle","Molly","Natalie","Nicola","Olivia","Penelope","Pippa","Rachel","Rebecca","Rose","Ruth","Sally","Samantha","Sarah","Sonia","Sophie","Stephanie","Sue","Theresa","Tracey","Una","Vanessa","Victoria","Virginia","Wanda","Wendy","Yvonne","Zoe","Adam","Adrian","Alan","Alexander","Andrew","Anthony","Austin","Benjamin","Blake","Boris","Brandon","Brian","Cameron","Carl","Charles","Christian","Christopher","Colin","Connor","Dan","David","Dominic","Dylan","Edward","Eric","Evan","Frank","Gavin","Gordon","Harry","Ian","Isaac","Jack","Jacob","Jake","James","Jason","Joe","John","Jonathan","Joseph","Joshua","Julian","Justin","Keith","Kevin","Leonard","Liam","Lucas","Luke","Matt","Max","Michael","Nathan","Neil","Nicholas","Oliver","Owen","Paul","Peter","Phil","Piers","Richard","Robert","Ryan","Sam","Sean","Sebastian","Simon","Stephen","Steven","Stewart","Thomas","Tim","Trevor","Victor","Warren","William"};
    String[] word = {"cheap","three","thoughtless","acceptable","yellow","eggnog","rely","exchange","abandoned","cause","order","pet","potato","grin","tired","earth","faithful","jog","lick","fold","innate","periodic","birds","dusty","resolute","unwieldy","stream","surprise","high","can","vanish","employ","poor","snobbish","swim","shrug","needless","measly","boy","extra-large","mammoth","puzzling","flag","need","economic","macabre","gainful","notebook","hug","note","industrious","argument","bustling","temporary","temper","sheet","loaf","silver","miss","cemetery","pop","plastic","sink","gratis","parsimonious","educated","marvelous","witty","hop","fierce","afford","abounding","birth","suit","tangy","basketball","guarantee","classy","drain","better","pleasure","groan","melted","part","carriage","satisfying","room","calculator","tan","inconclusive","women","tidy","tremble","cherry","confess","chunky","rot","handsome","sip","bless","rice","son","bitter","spotless","ink","complete","bushes","frame","toad","flock","moldy","likeable","worthless","panicky","cabbage","weary","call","kiss","sidewalk","ablaze","romantic","undress","unknown","pin","resonant","mend","lighten","turn","reflective","cactus","lean","girls","tail","steam","synonymous","crack","fax","cast","fertile","upset","action","telephone","pricey","theory","guess","rings","robin","basin","cruel","practise","sore","rich","capricious","tiny","jeans","wood","salty","record","lip","interest","school","annoying","screw","spot","pies","sleepy","guitar","competition","magnificent","march","well-groomed","elegant","adhesive","cycle","attend","harsh","trite","coat","door","tree","utter","spell","crayon","brown","quarter","judge","necessary","mark","difficult","scrawny","interrupt","dry","table","venomous","acidic","melodic","mitten","cakes","flippant","scatter"};
    String[] animal = {"Dog","Squirrel","Cougar","Raven","Cat","Alligator","Polar Bear","Possum","Lion","Crocodile","Bear","Koala","Tiger","Chipmunks","Grizzly","Swan","Rabbit","Whale","Platypus","Goat","Duck","Octopus","Sheep","Zebra","Anaconda","Cow","Goose","Eagle","Pig","Donkey","Osprey","Parrot","Panda","Poodle","Elephant","Skunk","Bull","Dolphin","Jaguar","Rooster","Salamander","Tortoise","Chicken","Raccoon","Hedgehog","Piranha","Penguin","Iguana","Turkey","Polar Bear","Flamingo","Crow","Walrus","Worms","Ape","Jellyﬁsh","Frog","Monkey","Giraffe","Sloth","Pigeon","Ostrich","Lobster","Wolf","Swordﬁsh","Cod","Deer","Mackerel","Gecko","Antelope","Bonito","Spider","Catﬁsh","Trout","Cobra","Whale","Falcon","Hamster","Fox","Chimpanzee","Seal","Lama","Kangaroo","Leopard","Camel","Porcupines","Cheetah","Gorilla","Hyena","Crab","Toad","Hippo","Mouse","Quail","Rhino","Rat","Moose","Crab","Tuna","Manta"};
    String[] city = {"New York City","Los Angeles","Chicago","Houston","Philadelphia","Phoenix","San Antonio","San Diego","Dallas","San Jose","Austin","Jacksonville","Indianapolis","San Francisco","Columbus","Fort Worth","Charlotte","Detroit","El Paso","Memphis","Boston","Seattle","Denver","Washington","Nashville-Davidson","Baltimore","Louisville/Jefferson","Portland","Oklahoma","Milwaukee","Las Vegas","Albuquerque","Tucson","Fresno","Sacramento","Long Beach","Kansas","Mesa","Virginia Beach","Atlanta","Colorado Springs","Raleigh","Omaha","Miami","Oakland","Tulsa","Minneapolis","Cleveland","Wichita","Arlington","New Orleans","Bakersfield","Tampa","Honolulu","Anaheim","Aurora","Santa Ana","St. Louis","Riverside","Corpus Christi","Pittsburgh","Lexington-Fayette","Anchorage","Stockton","Cincinnati","Buffalo","Orlando"};
    String[] lastname = {"Naczk","Friebe","Medina","Arnold","Chase","Blake","Blackburn","Chan","Adkins","Pacheco","Barry","Jordan","Bowers","Pitts","Duarte","Dean","Wilkinson","Mcintyre","Joseph","Melton","Summers","Mcintosh","Moody","Pollard","Tran","Delacruz","Cox","Tucker","Wilkerson","Lindsey","Boone","Goodwin","Doyle","Hanson","Velazquez","Camacho","Moss","Tanner","Clark","Bautista","Glenn","Mccormick","Blackwell","Orozco","Clay","Perez","Cervantes","Vance","Lynn","Berry","Perkins","Cherry","Mejia","Rocha","Rosales","Simmons","Calhoun","Mcconnell","Mckee","Davis","Horne","Skinner","Galloway","Cardenas","Villa","Woods","Arroyo","Galvan","Castillo","Sanders","Flowers","Barr","Montes","Briggs","Maynard","Bradshaw","Higgins","Hart","Duke","Alvarez","Hill","Davenport","Douglas","Olsen","Noble","Mason","Parks","Allison","Braun","Bean","Curry","Cummings","Kent","Dodson","Best","Jacobson","Guzman","Hale","Mccarty","Meza","Maxwell","Mcneil","Abraham","Allan","Alsop","Anderson","Arnold","Avery","Bailey","Baker","Ball","Bell","Berry","Black","Blake","Bond","Bower","Brown","Buckland","Burgess","Butler","Cameron","Campbell","Carr","Chapman","Churchill","Clark","Clarkson","Coleman","Cornish","Davidson","Davies","Dickens","Dowd","Duncan","Dyer","Edmunds","Ellison","Ferguson","Fisher","Forsyth","Fraser","Gibson","Gill","Glover","Graham","Grant","Gray","Greene","Hamilton","Hardacre","Harris","Hart","Hemmings","Henderson","Hill","Hodges","Howard","Hudson","Hughes","Hunter","Ince","Jackson","James","Johnston","Jones","Kelly","Kerr","King","Knox","Lambert","Langdon","Lawrence","Lee","Lewis","Lyman","MacDonald","Mackay","Mackenzie","MacLeod","Manning","Marshall","Martin","Mathis","May","McDonald","McLean","McGrath","Metcalfe","Miller","Mills","Mitchell","Morgan","Morrison","Murray","Nash","Newman","Nolan","North","Ogden","Oliver","Paige","Parr","Parsons","Paterson","Payne","Peake","Peters","Piper","Poole","Powell","Pullman","Quinn","Rampling","Randall","Rees","Reid","Roberts","Robertson","Ross","Russell","Rutherford","Sanderson","Scott","Sharp","Short","Simpson","Skinner","Slater","Smith","Springer","Stewart","Sutherland","Taylor","Terry","Thomson","Tucker","Turner","Underwood","Vance","Vaughan","Walker","Wallace","Walsh","Watson","Welch","White","Wilkins","Wilson","Wright","Young"};
    String[] colour = {"IndianRed","LightCoral","Salmon","DarkSalmon","LightSalmon","Crimson","Red","FireBrick","DarkRed","Pink","LightPink","HotPink","DeepPink","MediumVioletRed","PaleVioletRed","LightSalmon","Coral","Tomato","OrangeRed","DarkOrange","Orange","Gold","Yellow","LightYellow","LemonChiffon","LightGoldenRodYellow","PapayaWhip","Moccasin","PeachPuff","PaleGoldenrod","Khaki","DarkKhaki","Lavender","Thistle","Plum","Violet","Orchid","Fuchsia","Magenta","MediumOrchid","MediumPurple","BlueViolet","DarkViolet","DarkOrchid","DarkMagenta","Purple","Indigo","SlateBlue","DarkSlateBlue","MediumSlateBlue","GreenYellow","Chartreuse","LawnGreen","Lime","LimeGreen","PaleGreen","LightGreen","MediumSpringGreen","SpringGreen","MediumSeaGreen","SeaGreen","ForestGreen","Green","DarkGreen","YellowGreen","OliveDrab","Olive","DarkOliveGreen","MediumAquamarine","DarkSeaGreen","LightSeaGreen","DarkCyan","Teal","Aqua","Cyan","LightCyan","PaleTurquoise","Aquamarine","Turquoise","MediumTurquoise","DarkTurquoise","CadetBlue","SteelBlue","LightSteelBlue","PowderBlue","LightBlue","SkyBlue","LightSkyBlue","DeepSkyBlue","DodgerBlue","CornflowerBlue","MediumSlateBlue","RoyalBlue","Blue","MediumBlue","DarkBlue","Navy","MidnightBlue","Cornsilk","BlanchedAlmond","Bisque","NavajoWhite","Wheat","BurlyWood","Tan","RosyBrown","SandyBrown","Goldenrod","DarkGoldenrod","Peru","Chocolate","SaddleBrown","Sienna","Brown","Maroon","White","Snow","Honeydew","MintCream","Azure","AliceBlue","GhostWhite","WhiteSmoke","Seashell","Beige","OldLace","FloralWhite","Ivory","AntiqueWhite","Linen","LavenderBlush","MistyRose","Gainsboro","LightGray","Silver","DarkGray","Gray","DimGray","LightSlateGray","SlateGray","DarkSlateGray","Black"};
    String[] country = {"Qatar","Luxembourg","Singapore","Brunei Darussalam","Kuwait","Norway","United Arab Emirates","Hong Kong","United States","Switzerland","Saudi Arabia","Bahrain","Netherlands","Ireland","Australia","Austria","Sweden","Germany","Taiwan","Canada","Denmark","Oman","Iceland","Belgium","France","Finland","United Kingdom","Japan","Republic Of Korea","New Zealand","Italy","Spain","Israel","Malta","Trinidad And Tobago","Slovenia","Equatorial Guinea","Czech Republic","Slovak Republic","Cyprus","Lithuania","Estonia","Portugal","Greece","Malaysia","Bahamas","Poland","Seychelles","Hungary","Kazakhstan","Russian Federation","Latvia","Chile","Antigua And Barbuda","Gabon","Argentina","Panama","Uruguay","Saint Kitts And Nevis","Croatia","Romania","Turkey","Libyan Arab Jamahiriya","Azerbaijan","Belarus","Mauritius","Mexico","Lebanon","Bulgaria","Bolivarian Republic Of Venezuela","Suriname","Iran","Botswana","Barbados","Montenegro","Palau","Turkmenistan","Costa Rica","Brazil","Thailand","Algeria","Colombia","China","Macedonia","Iraq","Dominican Republic","South Africa","Maldives","Serbia","Peru","Jordan","Grenada","Tunisia","Ecuador","Albania","Egypt","Saint Lucia","Saint Vincent And The Grenadines","Namibia","Sri Lanka","Mongolia","Dominica","Indonesia","Bosnia And Herzegovina","Jamaica","Paraguay","Angola","Fiji","Ukraine","Belize","El Salvador","Bhutan","Georgia","Morocco","Swaziland","Armenia","Guatemala","Philippines","Timor Leste","Guyana","Rep.Congo","Cape Verde","Bolivia","Nigeria","India","Viet Nam","Uzbekistan","Lao People S Democratic Republic","Samoa","Tonga","Myanmar","Moldova","Nicaragua","Pakistan","Honduras","Sudan","Zambia","Ghana","Yemen","Bangladesh","Mauritania","Kyrgyz Republic","Cambodia","Sao Tome And Principe","Kenya","Marshall Islands","Micronesia","Djibouti","Cameroon","Lesotho","Côte D Ivoir"};
    String[] sound = {"an","au","be","ch","da","de","di","ei","el","en","er","es","ge","he","ht","ic","ie","in","it","le","li","nd","ne","ng","re","sc","se","si","st","te","un","en","er","ch","de","ei","ie","in","te","ge","un","en","er","sch","de","ei","ie","in","wi","te","ge","un","wo","ko","ir","re","ni","il"};
    String[] position = {"C","G","T","QB","RB","WR","TE","DT","DE","MLB","OLB","CB","S","K","H","LS","P","KOS","KR","PR"};
    String[] plant = {"Aloe Vera","Alfalfa","American Coffee Berry Tree","Bloodroot","Bouncing Bet","Bull Nettle","Bracken or Brake Fern","Burning Bush","Buttercup","Carelessweed ","Castor Bean","Chrysanthemums","Clover","Cocklebur","Creeping Charlie","Crown of Thorns","Curly Dock","Daffodil","Daphne","Delphinium","Devil's Trumpet","Dogbane","Dutchman's Breeches","Elderberry","Ergot","Fern","Fireweed","Foxglove","Poison Hemlock","Water Hemlock","Hemp","Horse Chestnut, Buckeyes","Horse Nettle","Horsetails","Hyacinth","Hydrangea","English Ivy","Ground Ivy","Poison Ivy","Jack-in-the-Pulpit","Japanese Yew","Jerusalem Cherry","Jimson Weed","Kalanchoe","Kentucky Coffee Tree","Kentucky Mahagony Tree","Klamath Weed","Lamb's Quarters","Lantana","Larkspur","Daylily","True Lily","Lily-of-the-Valley","Lupine","Mad Apple","Maple, Red","Mayapple","Milkweed","Mint","Mountain Laurel","Nicker Tree","Nightshade","Oleander","Ohio Buckeye","Philodendron","Pigweed","Poinsettia","Poke","Purple Mint","Redroot","Rhododendron","Rhubarb","Rosary Pea","Squirrelcorn","Staggerweed","St. Johnswort","Stinging Nettle","Stink Weed","Stump Tree","Sudan Grass","Summer Cypress","Thorn Apple","Tulip","White Snakeroot","Wild Onion","Yellow Sage"};

    /**
     *
     * @param seed
     * @param schemaDef
     */
    public ExtensionMaker(
            Long seed,
            SchemaDef schemaDef,
            Integer idOffset,
            Integer minEntities,
            Integer maxEntities ) {

        this.seed           = seed;
        this.schemaDef      = schemaDef;
        this.rand           = new Random(this.seed);
        this.idOffset       = idOffset;
        this.minEntities    = minEntities;
        this.maxEntities    = maxEntities;
    }

    /**
     *
     * @return
     */
    public List<String> buildStatements() {
        Map<String, List<Map<String, String>>>  genExtensionByTableMap;
        List<TableDef>                          tableDefList;

        genExtensionByTableMap  = new LinkedHashMap<>();
        tableDefList            = schemaDef.getTableDefList();

        List<String[][]> Extensionlist = new ArrayList<>();
        int tables = schemaDef.getTableDefList().size();
        int [] row = new int [tables];

        //goes through every table given
        for(int t = 0; t< tables; t++){
            TableDef tableDef = tableDefList.get(t);
            List<Map<String, String>> rowList = new ArrayList<>();

            Integer rowCount = randomBetween(this.minEntities, this.maxEntities);
            row[t] = rowCount;

            String[][] out = new String[row[t]][tableDef.getColumnDefList().size()];

            int column = tableDef.getColumnDefList().size();

            //variables used for combined keys
            int comp = 0;
            for(int j = 0; j < column; j++) {
                ColumnDef columnDef = tableDef.getColumnDefList().get(j);
                if(columnDef.isPrimary()
                        && columnDef.getMetaValueSet() == ColumnDef.META_VALUE_SET_FOREIGN_KEY) {
                    comp++;
                }
            }
            int [] comMember = new int [comp];
            boolean[][] com2 = new boolean[row[t]][row[t]];
            boolean[][][] com3 = new boolean[row[t]][row[t]][row[t]];

            //goes 	through row given
            for(int i = 0; i < rowCount; i++) {
                Map<String, String> entityMap = new LinkedHashMap<>();

                int comCount = 0;

                //goes through column and fills it whit an random attribute according to its MetaValueSet
                for(int j = 0; j < column; j++) {
                    ColumnDef columnDef = tableDef.getColumnDefList().get(j);


                    if (!columnDef.isNotNull() && chance(nullChance)){
                        out[i][j] = "NULL";
                    }else{
                        switch (columnDef.getMetaValueSet()) {
                            case ColumnDef.META_VALUE_SET_MAIL:
                                for ( int b = 0; b <= j; b ++){
                                    int set = tableDef.getColumnDefList().get(b).getMetaValueSet();
                                    if((set == ColumnDef.META_VALUE_SET_NAME ) || (set == ColumnDef.META_VALUE_SET_LASTNAME)){
                                        if(out[i][j] == null ){
                                            out[i][j] = out[i][b];
                                        }else{
                                            out[i][j] = out[i][j] + "." + out[i][b];
                                        }
                                    }
                                }
                                out[i][j] = out[i][j] + random(this.minEntities) +"@"+ mail[random(mail.length)];
                                break;
                            case ColumnDef.META_VALUE_SET_FIRSTNAME:
                                out[i][j] = gen(firstname);
                                break;
                            case ColumnDef.META_VALUE_SET_FOREIGN_KEY:
                                int number = random(this.idOffset, this.minEntities);
                                out[i][j] = "" + number;
                                if(columnDef.isPrimary()) {
                                    comMember[comCount] = number - idOffset;
                                    comCount++;
                                    if (comCount == comp) {
                                        if (comp == 2) {
                                            if (!com2[comMember[0]][comMember[1]]) {
                                                com2[comMember[0]][comMember[1]] = true;
                                            } else {
                                                comCount--;
                                                out[i][j] = null;
                                                j--;
                                            }
                                        }
                                        if (comp == 3) {
                                            if (!com3[comMember[0]][comMember[1]][comMember[2]]) {
                                                com3[comMember[0]][comMember[1]][comMember[2]] = true;
                                            } else {
                                                comCount--;
                                                out[i][j] = null;
                                                j--;
                                            }
                                        }

                                    }
                                }
                                break;
                            case ColumnDef.META_VALUE_SET_ID:
                                out[i][j] = "" + (idOffset +  i);
                                break;
                            case ColumnDef.META_VALUE_SET_ANIMAL:
                                out[i][j] = gen(animal);
                                break;
                            case ColumnDef.META_VALUE_SET_CITY:
                                out[i][j] = gen(city);
                                break;
                            case ColumnDef.META_VALUE_SET_POSITION:
                                out[i][j] = gen(position);
                                break;
                            case ColumnDef.META_VALUE_SET_TITLE:
                                out[i][j] = gen(title);
                                break;
                            case ColumnDef.META_VALUE_SET_FULLNAME:
                                out[i][j] = gen(firstname) + gen(lastname);
                                break;
                            case ColumnDef.META_VALUE_SET_METAL:
                                out[i][j] = gen(metal);
                                break;
                            case ColumnDef.META_VALUE_SET_DAY:
                                out[i][j] = "" + random(1,30);
                                break;
                            case ColumnDef.META_VALUE_SET_NAME:
                                out[i][j] = gen(firstname);
                                break;
                            case ColumnDef.META_VALUE_SET_MONTH:
                                out[i][j] = "" + random(1,12);
                                break;
                            case ColumnDef.META_VALUE_SET_YEAR:
                                out[i][j] = "" + random(1930,88);
                                break;
                            case ColumnDef.META_VALUE_SET_LOREM_IPSUM:
                                out[i][j] = gen(word) + gen(word);
                                break;
                            case ColumnDef.META_VALUE_SET_LASTNAME:
                                out[i][j] = gen(lastname);
                                break;
                            case ColumnDef.META_VALUE_SET_COLOR:
                                out[i][j] = gen(colour);
                                break;
                            case ColumnDef.META_VALUE_SET_DATE:
                                GregorianCalendar gc = new GregorianCalendar();
                                int year = random(1930,87);

                                gc.set(Calendar.YEAR, year);

                                int dayOfYear = random(1,gc.getActualMaximum(Calendar.DAY_OF_YEAR));

                                gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

                                out[i][j] = (gc.get(Calendar.YEAR) + "-" );
                                if(gc.get(Calendar.MONTH) >8){
                                    out[i][j]= out[i][j].concat(""+ (gc.get(Calendar.MONTH) + 1));
                                }else{
                                    out[i][j]= out[i][j].concat("0"+ (gc.get(Calendar.MONTH) + 1));
                                }
                                if(gc.get(Calendar.DAY_OF_MONTH) >9){
                                    out[i][j]= out[i][j].concat("-"+ (gc.get(Calendar.DAY_OF_MONTH)));
                                }else{
                                    out[i][j]= out[i][j].concat("-0"+ (gc.get(Calendar.DAY_OF_MONTH)));
                                }
                                break;
                            case ColumnDef.META_VALUE_SET_PLANT:
                                out[i][j] = gen(plant);
                                break;
                            case ColumnDef.META_VALUE_SET_GRADE:
                                out[i][j] = "" + (random(10,41)/10);
                                break;
                            case ColumnDef.META_VALUE_SET_LOCATION:
                                out[i][j] = gen(country);
                                break;
                            default:
                                switch (columnDef.getDataType()) {
                                    case "INT":case "int":
                                        int min = columnDef.getMinValueSet();
                                        int max = columnDef.getMaxValueSet();
                                        if( max != 0){
                                            out[i][j] = "" + random(min,max - min);
                                        }else{
                                            out[i][j] = "" + random(1111);
                                        }
                                        break;
                                    case "VARCHAR(255)":case "VARCHAR":
                                        int num = random(2,4);
                                        out[i][j] = "";
                                        while ( num > 0 ){
                                            out[i][j] += gen(sound);
                                            num--;
                                        }
                                        break;
                                    default:
                                        out[i][j] = "" + columnDef.getName() + columnDef.getDataType();
                                        break;
                                }
                                break;
                        }
                    }
                    entityMap.put(columnDef.getName(), out[i][j]);
                }

                rowList.add(entityMap);
            }
            Extensionlist.add(out);
            genExtensionByTableMap.put(tableDef.getName(), rowList);
            /*tableMap.entrySet().forEach(entry -> {
                System.out.println(entry.getKey());
            });*/
        }


        // InsertStatement Generator

        List<String> insertStatementList = new ArrayList<>();
        genExtensionByTableMap.forEach((tableName, entityList) -> {
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