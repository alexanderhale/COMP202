public class Test {
  public static void main(String[] args) {
    // testing DocumentFrequency
    System.out.println(DocumentFrequency.extractDocumentFrequencies("./docs", 40).size());
    System.out.println(DocumentFrequency.extractDocumentFrequencies("./docs", 40).get("a"));
  }
}