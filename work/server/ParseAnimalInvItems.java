public class ParseAnimalInvItems {
  private static void printItem(String raw) {
    String[] parts = raw.split("\\|",2);
    String[] db = parts[0].split(",");
    String[] at = parts[1].split(",");
    int template = Integer.parseInt(db[1]);
    int place = Integer.parseInt(db[2]);
    int plus = Integer.parseInt(db[5]);
    int level = Integer.parseInt(db[6]);
    int color = Integer.parseInt(db[11]);
    int hang = Integer.parseInt(db[12]);
    int he = Integer.parseInt(db[13]);
    int lock = Integer.parseInt(db[14]);
    int idAnimal = Integer.parseInt(db[16]);
    int lock0 = Integer.parseInt(at[58]);
    int lock1 = Integer.parseInt(at[59]);
    int lock2 = Integer.parseInt(at[60]);
    System.out.println("template="+template+" place="+place+" plus="+plus+" level="+level+" color="+color+" hang="+hang+" he="+he+" lock="+lock+" idAnimal="+idAnimal+" lockAtb="+lock0+","+lock1+","+lock2+" raw="+parts[0]);
  }
  public static void main(String[] args) throws Exception {
    java.nio.file.Path p = java.nio.file.Paths.get("exports\\poseidon_animal_inventory_item_20260427_213502\\after_inven.txt");
    String inven = java.nio.file.Files.readString(p);
    for(String item: inven.split(">")) {
      if(item.isEmpty()) continue;
      if(item.startsWith("0,515,") || item.startsWith("0,517,")) printItem(item);
    }
  }
}
