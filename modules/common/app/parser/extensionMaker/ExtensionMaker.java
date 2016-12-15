package parser.extensionMaker;

import models.SchemaDef;
import models.TableDef;

import java.util.List;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;

/**
 * @author carl
 */
public class ExtensionMaker {
    private final Long      seed;
    private final SchemaDef schemaDef;
    private final Random    rand;

    String[] mail = {"tu-bs.de","aol.com","att.net","comcast.net","facebook.com","gmail.com","gmx.com","googlemail.com","google.com","hotmail.com","hotmail.co.uk","mac.com","me.com","mail.com","msn.com","live.com","sbcglobal.net","verizon.net","yahoo.com","yahoo.co.uk"};
    String[] metal = {"Calcium","Strontium","Barium","Radium","Aluminum","Gallium","Indium","Tin","Thallium","Lead","Bismuth","Scandium","Titanium","Vanadium","Chromium","Manganese","Iron","Cobalt","Nickel","Copper","Zinc","Yttrium","Zirconium","Niobium","Molybdenum","Technetium","Ruthenium","Rhodium","Palladium","Silver","Cadmium","Lanthanum","Hafnium","Tantalum","Tungsten","Rhenium","Osmium","Iridium","Platinum","Gold"};
    String[] title = {"1984","A Doll's House","A Sentimental Education","Absalom, Absalom!","The Adventures of Huckleberry Finn","The Aeneid","Anna Karenina","Beloved","Berlin Alexanderplatz","Blindness","The Book of Disquiet","The Book of Jo","The Brothers Karamazov","Buddenbrooks","Canterbury Tales","The Castle","Children of Gebelawi","Collected Fictions","Complete Poems","The Complete Stories","The Complete Tales","Confessions of Zeno","Crime and Punishment","Dead Souls","The Death of Ivan Ilyich and Other Stories","Decameron","The Devil to Pay in the Backlands","Diary of a Madman and Other Stories","The Divine Comedy","Don Quixote","Essays","Fairy Tales and Stories","Faust","Gargantua and Pantagruel","The Golden Notebook","Great Expectations","Gulliver's Travels","Gypsy Ballads","Hamlet","History","Hunger","The Idiot","The Iliad","Independent People","Invisible Man","Jacques the Fatalist and His Master","Journey to the End of the Night","King Lear","Leaves of Grass","The Life and Opinions of Tristram Shandy","Lolita","Love in the Time of Cholera","Madame Bovary","The Magic Mountain","Mahabharat","The Man Without Qualities","The Mathnaw","Medea","Memoirs of Hadrian","Metamorphoses","Middlemarch","Midnight's Children","Moby-Dick","Mrs. Dalloway","Njaals Sag","Nostromo","The Odyssey","Oedipus the King Sophocle","Old Goriot","The Old Man and the Sea","One Hundred Years of Solitude","The Orchard","Othello","Pedro Paramo","Pippi Longstocking","Poems","The Possessed","Pride and Prejudice","The Ramayana","The Recognition of Sakuntala","The Red and the Black","Remembrance of Things Past","Season of Migration to the North","Sons and Lovers","The Sound and the Fury","The Sound of the Mountain","The Stranger","The Tale of Genji","Things Fall Apart","Thousand and One Night","The Tin Drum","To the Lighthouse","The Trial","Trilogy: Molloy, Malone Dies, The Unnamable","Ulysses","War and Peace","Wuthering Heights","Zorba the Greek"};
    String[] firstname = {"Abigail","Alexandra","Alison","Amanda","Amelia","Amy","Andrea","Angela","Anna","Anne","Audrey","Ava","Bella","Bernadette","Carol","Caroline","Carolyn","Chloe","Claire","Deirdre","Diana","Diane","Donna","Dorothy","Elizabeth","Ella","Emily","Emma","Faith","Felicity","Fiona","Gabrielle","Grace","Hannah","Heather","Irene","Jan","Jane","Jasmine","Jennifer","Jessica","Joan","Joanne","Julia","Karen","Katherine","Kimberly","Kylie","Lauren","Leah","Lillian","Lily","Lisa","Madeleine","Maria","Mary","Megan","Melanie","Michelle","Molly","Natalie","Nicola","Olivia","Penelope","Pippa","Rachel","Rebecca","Rose","Ruth","Sally","Samantha","Sarah","Sonia","Sophie","Stephanie","Sue","Theresa","Tracey","Una","Vanessa","Victoria","Virginia","Wanda","Wendy","Yvonne","Zoe","Adam","Adrian","Alan","Alexander","Andrew","Anthony","Austin","Benjamin","Blake","Boris","Brandon","Brian","Cameron","Carl","Charles","Christian","Christopher","Colin","Connor","Dan","David","Dominic","Dylan","Edward","Eric","Evan","Frank","Gavin","Gordon","Harry","Ian","Isaac","Jack","Jacob","Jake","James","Jason","Joe","John","Jonathan","Joseph","Joshua","Julian","Justin","Keith","Kevin","Leonard","Liam","Lucas","Luke","Matt","Max","Michael","Nathan","Neil","Nicholas","Oliver","Owen","Paul","Peter","Phil","Piers","Richard","Robert","Ryan","Sam","Sean","Sebastian","Simon","Stephen","Steven","Stewart","Thomas","Tim","Trevor","Victor","Warren","William"};
    String[] word = {"cheap","three","thoughtless","acceptable","yellow","eggnog","rely","exchange","abandoned","cause","order","pet","potato","grin","tired","earth","faithful","jog","lick","fold","innate","periodic","birds","dusty","resolute","unwieldy","stream","surprise","high","can","vanish","employ","poor","snobbish","swim","shrug","needless","measly","boy","extra-large","mammoth","puzzling","flag","need","economic","macabre","gainful","notebook","hug","note","industrious","argument","bustling","temporary","temper","sheet","loaf","silver","miss","cemetery","pop","plastic","sink","gratis","parsimonious","educated","marvelous","witty","hop","fierce","afford","abounding","birth","suit","tangy","basketball","guarantee","classy","drain","better","pleasure","groan","melted","part","carriage","satisfying","room","calculator","tan","inconclusive","women","tidy","tremble","cherry","confess","chunky","rot","handsome","sip","bless","rice","son","bitter","spotless","ink","complete","bushes","frame","toad","flock","moldy","likeable","worthless","panicky","cabbage","weary","call","kiss","sidewalk","ablaze","romantic","undress","unknown","pin","resonant","mend","lighten","turn","reflective","cactus","lean","girls","tail","steam","synonymous","crack","fax","cast","fertile","upset","action","telephone","pricey","theory","guess","rings","robin","basin","cruel","practise","sore","rich","capricious","tiny","jeans","wood","salty","record","lip","interest","school","annoying","screw","spot","pies","sleepy","guitar","competition","magnificent","march","well-groomed","elegant","adhesive","cycle","attend","harsh","trite","coat","door","tree","utter","spell","crayon","brown","quarter","judge","necessary","mark","difficult","scrawny","interrupt","dry","table","venomous","acidic","melodic","mitten","cakes","flippant","scatter"};
    String[] animal = {"Dog","Squirrel","Cougar","Raven","Cat","Alligator","Polar Bear","Possum","Lion","Crocodile","Bear","Koala","Tiger","Chipmunks","Grizzly","Swan","Rabbit","Whale","Platypus","Goat","Duck","Octopus","Sheep","Zebra","Anaconda","Cow","Goose","Eagle","Pig","Donkey","Osprey","Parrot","Panda","Poodle","Elephant","Skunk","Bull","Dolphin","Jaguar","Rooster","Salamander","Tortoise","Chicken","Raccoon","Hedgehog","Piranha","Penguin","Iguana","Turkey","Polar Bear","Flamingo","Crow","Walrus","Worms","Ape","Jellyﬁsh","Frog","Monkey","Giraffe","Sloth","Pigeon","Ostrich","Lobster","Wolf","Swordﬁsh","Cod","Deer","Mackerel","Gecko","Antelope","Bonito","Spider","Catﬁsh","Trout","Cobra","Whale","Falcon","Hamster","Fox","Chimpanzee","Seal","Lama","Kangaroo","Leopard","Camel","Porcupines","Cheetah","Gorilla","Hyena","Crab","Toad","Hippo","Mouse","Quail","Rhino","Rat","Moose","Crab","Tuna","Manta"};
    String[] city = {"New York City","Los Angeles","Chicago","Houston","Philadelphia","Phoenix","San Antonio","San Diego","Dallas","San Jose","Austin","Jacksonville","Indianapolis","San Francisco","Columbus","Fort Worth","Charlotte","Detroit","El Paso","Memphis","Boston","Seattle","Denver","Washington","Nashville-Davidson","Baltimore","Louisville/Jefferson","Portland","Oklahoma","Milwaukee","Las Vegas","Albuquerque","Tucson","Fresno","Sacramento","Long Beach","Kansas","Mesa","Virginia Beach","Atlanta","Colorado Springs","Raleigh","Omaha","Miami","Oakland","Tulsa","Minneapolis","Cleveland","Wichita","Arlington","New Orleans","Bakersfield","Tampa","Honolulu","Anaheim","Aurora","Santa Ana","St. Louis","Riverside","Corpus Christi","Pittsburgh","Lexington-Fayette","Anchorage","Stockton","Cincinnati","Buffalo","Orlando"};
    String[] lastname = {"Medina","Arnold","Chase","Blake","Blackburn","Chan","Adkins","Pacheco","Barry","Jordan","Bowers","Pitts","Duarte","Dean","Wilkinson","Mcintyre","Joseph","Melton","Summers","Mcintosh","Moody","Pollard","Tran","Delacruz","Cox","Tucker","Wilkerson","Lindsey","Boone","Goodwin","Doyle","Hanson","Velazquez","Camacho","Moss","Tanner","Clark","Bautista","Glenn","Mccormick","Blackwell","Orozco","Clay","Perez","Cervantes","Vance","Lynn","Berry","Perkins","Cherry","Mejia","Rocha","Rosales","Simmons","Calhoun","Mcconnell","Mckee","Davis","Horne","Skinner","Galloway","Cardenas","Villa","Woods","Arroyo","Galvan","Castillo","Sanders","Flowers","Barr","Montes","Briggs","Maynard","Bradshaw","Higgins","Hart","Duke","Alvarez","Hill","Davenport","Douglas","Olsen","Noble","Mason","Parks","Allison","Braun","Bean","Curry","Cummings","Kent","Dodson","Best","Jacobson","Guzman","Hale","Mccarty","Meza","Maxwell","Mcneil","Abraham","Allan","Alsop","Anderson","Arnold","Avery","Bailey","Baker","Ball","Bell","Berry","Black","Blake","Bond","Bower","Brown","Buckland","Burgess","Butler","Cameron","Campbell","Carr","Chapman","Churchill","Clark","Clarkson","Coleman","Cornish","Davidson","Davies","Dickens","Dowd","Duncan","Dyer","Edmunds","Ellison","Ferguson","Fisher","Forsyth","Fraser","Gibson","Gill","Glover","Graham","Grant","Gray","Greene","Hamilton","Hardacre","Harris","Hart","Hemmings","Henderson","Hill","Hodges","Howard","Hudson","Hughes","Hunter","Ince","Jackson","James","Johnston","Jones","Kelly","Kerr","King","Knox","Lambert","Langdon","Lawrence","Lee","Lewis","Lyman","MacDonald","Mackay","Mackenzie","MacLeod","Manning","Marshall","Martin","Mathis","May","McDonald","McLean","McGrath","Metcalfe","Miller","Mills","Mitchell","Morgan","Morrison","Murray","Nash","Newman","Nolan","North","Ogden","Oliver","Paige","Parr","Parsons","Paterson","Payne","Peake","Peters","Piper","Poole","Powell","Pullman","Quinn","Rampling","Randall","Rees","Reid","Roberts","Robertson","Ross","Russell","Rutherford","Sanderson","Scott","Sharp","Short","Simpson","Skinner","Slater","Smith","Springer","Stewart","Sutherland","Taylor","Terry","Thomson","Tucker","Turner","Underwood","Vance","Vaughan","Walker","Wallace","Walsh","Watson","Welch","White","Wilkins","Wilson","Wright","Young"};
    String[] colour = {"IndianRed","LightCoral","Salmon","DarkSalmon","LightSalmon","Crimson","Red","FireBrick","DarkRed","Pink","LightPink","HotPink","DeepPink","MediumVioletRed","PaleVioletRed","LightSalmon","Coral","Tomato","OrangeRed","DarkOrange","Orange","Gold","Yellow","LightYellow","LemonChiffon","LightGoldenRodYellow","PapayaWhip","Moccasin","PeachPuff","PaleGoldenrod","Khaki","DarkKhaki","Lavender","Thistle","Plum","Violet","Orchid","Fuchsia","Magenta","MediumOrchid","MediumPurple","BlueViolet","DarkViolet","DarkOrchid","DarkMagenta","Purple","Indigo","SlateBlue","DarkSlateBlue","MediumSlateBlue","GreenYellow","Chartreuse","LawnGreen","Lime","LimeGreen","PaleGreen","LightGreen","MediumSpringGreen","SpringGreen","MediumSeaGreen","SeaGreen","ForestGreen","Green","DarkGreen","YellowGreen","OliveDrab","Olive","DarkOliveGreen","MediumAquamarine","DarkSeaGreen","LightSeaGreen","DarkCyan","Teal","Aqua","Cyan","LightCyan","PaleTurquoise","Aquamarine","Turquoise","MediumTurquoise","DarkTurquoise","CadetBlue","SteelBlue","LightSteelBlue","PowderBlue","LightBlue","SkyBlue","LightSkyBlue","DeepSkyBlue","DodgerBlue","CornflowerBlue","MediumSlateBlue","RoyalBlue","Blue","MediumBlue","DarkBlue","Navy","MidnightBlue","Cornsilk","BlanchedAlmond","Bisque","NavajoWhite","Wheat","BurlyWood","Tan","RosyBrown","SandyBrown","Goldenrod","DarkGoldenrod","Peru","Chocolate","SaddleBrown","Sienna","Brown","Maroon","White","Snow","Honeydew","MintCream","Azure","AliceBlue","GhostWhite","WhiteSmoke","Seashell","Beige","OldLace","FloralWhite","Ivory","AntiqueWhite","Linen","LavenderBlush","MistyRose","Gainsboro","LightGray","Silver","DarkGray","Gray","DimGray","LightSlateGray","SlateGray","DarkSlateGray","Black"};
    String[] country = {"Qatar","Luxembourg","Singapore","Brunei Darussalam","Kuwait","Norway","United Arab Emirates","Hong Kong","United States","Switzerland","Saudi Arabia","Bahrain","Netherlands","Ireland","Australia","Austria","Sweden","Germany","Taiwan","Canada","Denmark","Oman","Iceland","Belgium","France","Finland","United Kingdom","Japan","Republic Of Korea","New Zealand","Italy","Spain","Israel","Malta","Trinidad And Tobago","Slovenia","Equatorial Guinea","Czech Republic","Slovak Republic","Cyprus","Lithuania","Estonia","Portugal","Greece","Malaysia","Bahamas","Poland","Seychelles","Hungary","Kazakhstan","Russian Federation","Latvia","Chile","Antigua And Barbuda","Gabon","Argentina","Panama","Uruguay","Saint Kitts And Nevis","Croatia","Romania","Turkey","Libyan Arab Jamahiriya","Azerbaijan","Belarus","Mauritius","Mexico","Lebanon","Bulgaria","Bolivarian Republic Of Venezuela","Suriname","Iran","Botswana","Barbados","Montenegro","Palau","Turkmenistan","Costa Rica","Brazil","Thailand","Algeria","Colombia","China","Macedonia","Iraq","Dominican Republic","South Africa","Maldives","Serbia","Peru","Jordan","Grenada","Tunisia","Ecuador","Albania","Egypt","Saint Lucia","Saint Vincent And The Grenadines","Namibia","Sri Lanka","Mongolia","Dominica","Indonesia","Bosnia And Herzegovina","Jamaica","Paraguay","Angola","Fiji","Ukraine","Belize","El Salvador","Bhutan","Georgia","Morocco","Swaziland","Armenia","Guatemala","Philippines","Timor Leste","Guyana","Rep.Congo","Cape Verde","Bolivia","Nigeria","India","Viet Nam","Uzbekistan","Lao People S Democratic Republic","Samoa","Tonga","Myanmar","Moldova","Nicaragua","Pakistan","Honduras","Sudan","Zambia","Ghana","Yemen","Bangladesh","Mauritania","Kyrgyz Republic","Cambodia","Sao Tome And Principe","Kenya","Marshall Islands","Micronesia","Djibouti","Cameroon","Lesotho","Côte D Ivoir"};
    String[] sound = {"an","au","be","ch","da","de","di","ei","el","en","er","es","ge","he","ht","ic","ie","in","it","le","li","nd","ne","ng","re","sc","se","si","st","te","un","en","er","ch","de","ei","ie","in","te","ge","un","en","er","sch","de","ei","ie","in","wi","te","ge","un","wo","ko","ir","re","ni","il"};
    String[] position = {"C","G","T","QB","RB","WR","TE","DT","DE","MLB","OLB","CB","S","K","H","LS","P","KOS","KR","PR"};

    public ExtensionMaker(Long seed, SchemaDef schemaDef) {
        this.seed = seed;
        this.schemaDef = schemaDef;
        this.rand = new Random(this.seed);
    }

    public ArrayList<String[][]> buildStatements() {

        ArrayList<String[][]> Extensionlist = new ArrayList<String[][]>();

        List<TableDef> tableDefs = schemaDef.getTableDefList();
        schemaDef.getTableDefList().forEach((tabledef) -> {
            System.out.println(tabledef.getName());
            tabledef.getColumnDefList().forEach(columnDef -> {
                System.out.println("  - " + columnDef.getName() + " " + columnDef.getDataType());
            });
        });

        schemaDef.getForeignKeyList().forEach(foreignKey -> {
            System.out.println(foreignKey.getName());
            foreignKey.getForeignKeyRelationList().forEach(foreignKeyRelation -> {
                System.out.println("  - " + foreignKeyRelation.getSourceColumn().getName() + " -> " + foreignKeyRelation.getTargetColumn().getName());
            });
        });

        int tables = schemaDef.getTableDefList().size();
        int row = 100;


        System.out.println("META: " + tableDefs.get(0).getColumnDefList().get(0).getMetaValueSet());

        //tableDefs.forEach(tableDef -> tableDef.getColumnDefList().forEach(columnDef -> System.out.println(columnDef.getName())));

        for(int t = 0; t< tables; t++){
            System.out.println(tableDefs.get(t));

            String[][] out = new String[row][tableDefs.get(t).getColumnDefList().size()];

            int columm = tableDefs.get(t).getColumnDefList().size();

            int comp = 0;
            int comcount;
            int number;
            for(int j = 0; j < columm; j++) {
                if(tableDefs.get(t).getColumnDefList().get(j).isPrimary() && tableDefs.get(t).getColumnDefList().get(j).getMetaValueSet() == 358) {
                    comp++;
                }
            }
            int [] commember = new int [comp];
            boolean[][] com2 = new boolean[row][row];
            boolean[][][] com3 = new boolean[row][row][row];


            for(int i = 0; i < row; i++) {
                comcount = 0;
                for(int j = 0; j < columm; j++) {
                    switch (tableDefs.get(t).getColumnDefList().get(j).getMetaValueSet()) {
                        case 1:
                            for ( int b = 0; b <= j; b ++){
                                if( (tableDefs.get(t).getColumnDefList().get(b).getMetaValueSet() == 8 ) || (tableDefs.get(t).getColumnDefList().get(b).getMetaValueSet() == 744)){
                                    if(out[i][j] == null ){
                                        out[i][j] = out[i][b];
                                    }else{out[i][j] = out[i][j] + "." + out[i][b];}
                                }
                            }
                            out[i][j] = out[i][j] + rand.nextInt(100) +"@"+ mail[rand.nextInt(mail.length)];
                            break;
                        case 8:
                            out[i][j] = "" + firstname[rand.nextInt(firstname.length)];
                            break;
                        case 358:
                            number = rand.nextInt(row);
                            out[i][j] = "" + number;
                            if(tableDefs.get(t).getColumnDefList().get(j).isPrimary()) {
                                commember[comcount] = number;
                                comcount++;
                                if (comcount == comp) {
                                    if (comp == 2) {
                                        if (!com2[commember[0]][commember[1]]) {
                                            com2[commember[0]][commember[1]] = true;
                                        } else {
                                            comcount--;
                                            out[i][j] = null;
                                            j--;
                                        }
                                    }
                                    if (comp == 3) {
                                        if (!com3[commember[0]][commember[1]][commember[2]]) {
                                            com3[commember[0]][commember[1]][commember[2]] = true;
                                        } else {
                                            comcount--;
                                            out[i][j] = null;
                                            j--;
                                        }
                                    }

                                }
                            }
                            break;
                        case 485:
                            out[i][j] = "" + i;
                            break;
                        case 2:
                            out[i][j] = "" + animal[rand.nextInt(animal.length)];
                            break;
                        case 3:
                            out[i][j] = "" + city[rand.nextInt(city.length)];
                            break;
                        case 33:
                            out[i][j] = "" + position[rand.nextInt(position.length)];
                            break;
                        case 4:
                            out[i][j] = "" + title[rand.nextInt(title.length)];
                            break;
                        case 5:
                            out[i][j] = "" + firstname[rand.nextInt(firstname.length)] + " " + lastname[rand.nextInt(lastname.length)];
                            break;
                        case 6:
                            out[i][j] = "" + metal[rand.nextInt(metal.length)];
                            break;
                        case 7:
                            out[i][j] = "" + rand.nextInt(30);
                            break;
                        case 768:
                            out[i][j] = "" + firstname[rand.nextInt(firstname.length)];
                            break;
                        case 9:
                            out[i][j] = "" + rand.nextInt(13);
                            break;
                        case 154:
                            out[i][j] = "" + (1930 + rand.nextInt(87));
                            break;
                        case 672:
                            out[i][j] = word[rand.nextInt(word.length)] + " " + word[rand.nextInt(word.length)];
                            break;
                        case 744:
                            out[i][j] = "" + lastname[rand.nextInt(lastname.length)];
                            break;
                        case 10:
                            out[i][j] = "" + colour[rand.nextInt(colour.length)];
                            break;
                        case 11:
                            out[i][j] = rand.nextInt(30) +"."+ rand.nextInt(13) +"."+ (1930 + rand.nextInt(87));
                            break;
                        case 952:
                            out[i][j] = "" + country[rand.nextInt(country.length)];
                            break;
                        default:
                            switch (tableDefs.get(t).getColumnDefList().get(j).getDataType()) {
                                case "INT":case "int":
                                    if(tableDefs.get(t).getColumnDefList().get(j).getMaxValueSet() != 0){
                                        out[i][j] = "" + ( tableDefs.get(t).getColumnDefList().get(j).getMinValueSet() + rand.nextInt(tableDefs.get(t).getColumnDefList().get(j).getMaxValueSet() - tableDefs.get(t).getColumnDefList().get(j).getMinValueSet()));
                                    }else{
                                        out[i][j] = "" + rand.nextInt(1111);
                                    }
                                    break;
                                case "VARCHAR(255)":case "VARCHAR":
                                    int num = 2 + rand.nextInt(4);
                                    out[i][j] = "";
                                    while ( num > 0 ){
                                        out[i][j] += sound[rand.nextInt(sound.length)];
                                        num--;
                                    }
                                    break;
                                default:
                                    out[i][j] = "" + tableDefs.get(t).getColumnDefList().get(j).getName() + tableDefs.get(t).getColumnDefList().get(j).getDataType();
                                    break;
                            }
                            break;
                    }
                }
            }
            Extensionlist.add(out);

        }

        return Extensionlist;
    }

    public String parseToStatmant(ArrayList<String[][]> args){
        int row = 100;

        String out = "";
        List<TableDef> tableDefs = schemaDef.getTableDefList();
        int tables = schemaDef.getTableDefList().size();
        System.out.print(tables + tableDefs.get(0).getName());
        for(int t = 0; t< tables; t++){
            int columm = tableDefs.get(t).getColumnDefList().size();
            String statment;

            statment = "INSERT INTO " + tableDefs.get(t).getName() + " (";


            for(int j = 0; j < columm; j++) {
                statment = statment.concat("" + tableDefs.get(t).getColumnDefList().get(j).getName());
                if(j +1 != columm ){
                    statment = statment.concat(",");
                }
            }
            statment = statment.concat(") " + "VALUES ");
            String[][] mat = new String[row][tableDefs.get(t).getColumnDefList().size()];

            mat = args.get(t);
            for(int i = 0; i < row; i++) {
                statment = statment.concat("(");
                for(int j = 0; j < columm; j++) {
                    statment = statment.concat("'" + mat[i][j] + "'");
                    if(j +1 != columm ){
                        statment = statment.concat(",");
                    }
                }
                statment = statment.concat(")");
                if(i +1 != row ){
                    statment = statment.concat(",");
                }
            }

            statment = statment.concat(";");

            out = out. concat(statment);



        }

        System.out.println("OUT:  "+ out);

        return out;
    }

    public Long getSeed() {
        return seed;
    }

    public SchemaDef getSchemaDef() {
        return schemaDef;
    }
}