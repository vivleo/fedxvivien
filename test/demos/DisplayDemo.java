import com.fluidops.fedx.Config;
import com.fluidops.fedx.FedXFactory;
import com.fluidops.fedx.FederationManager;
import com.fluidops.fedx.QueryManager;
import com.fluidops.fedx.endpoint.Endpoint;
import com.fluidops.fedx.endpoint.EndpointFactory;
import com.fluidops.fedx.monitoring.MonitoringUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sail.SailRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DisplayDemo extends Application {
    private String resultat ="";
    private Label result = new Label();
    private Label rowNumber = new Label();
    private int numberOfRow;
    private Label informations = new Label();


    @Override
    @SuppressWarnings("Duplicates")
    public void start(Stage primaryStage) throws Exception {
        final ToggleGroup tg = new ToggleGroup();
        Label title = new Label("Veuillez choisir la démo :");
        RadioButton r1 = new RadioButton("Démo 1");
        RadioButton r2 = new RadioButton("Démo 2");
        RadioButton r3 = new RadioButton("Démo 3");
        RadioButton r4 = new RadioButton("Démo 4");
        RadioButton r5 = new RadioButton("Démo 5");
        RadioButton r6 = new RadioButton("Démo 6");
        RadioButton r7 = new RadioButton("Démo 7");
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        r3.setToggleGroup(tg);
        r4.setToggleGroup(tg);
        r5.setToggleGroup(tg);
        r6.setToggleGroup(tg);
        r7.setToggleGroup(tg);
        result.setMinSize(1000,500);

        ScrollPane sp = new ScrollPane();
        sp.setContent(result);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        result.setText(resultat);
        Button b = new Button("lancement de la démo");
        Button clear = new Button("Clear");

        Button info1 = new Button("?");
        Button info2 = new Button("?");
        Button info3 = new Button("?");
        Button info4 = new Button("?");
        Button info5 = new Button("?");
        Button info6 = new Button("?");
        Button info7 = new Button("?");
        //VBox vbox = new VBox(title,r1, r2, r3, r4, r5, r6, r7);
        //vbox.setSpacing(5);
        GridPane radioButton = new GridPane();
        radioButton.add(title,0,0,2,1);
        radioButton.add(r1,0,1);
        radioButton.add(info1,1,1);

        radioButton.add(r2,0,2);
        radioButton.add(info2,1,2);

        radioButton.add(r3,0,3);
        radioButton.add(info3,1,3);

        radioButton.add(r4,0,4);
        radioButton.add(info4,1,4);

        radioButton.add(r5,0,5);
        radioButton.add(info5,1,5);

        radioButton.add(r6,0,6);
        radioButton.add(info6,1,6);

        radioButton.add(r7,0,7);
        radioButton.add(info7,1,7);

        Popup p1 = new Popup();
        p1.setAutoHide(true);
        Label i1 = new Label("Démo 1 : \n " +
                "Définition des Endpoints (et préfixe) dans un fichier de configuration\n" +
                "Fichier .ttl  ");
        i1.setMinSize(200,100);
        p1.getContent().add(i1);
        info1.setOnAction(event -> {
            if (!p1.isShowing())
                p1.show(primaryStage);
        });

        Popup p2 = new Popup();
        p2.setAutoHide(true);
        Label i2 = new Label("Démo 2 : \n" +
                "Définition d'un fichier de configuration. Permet par exemple d'activer le débuggage de requête,\n" +
                "action sur les logs, etc." +
                "Et comme pour la démo 1, on définit les Endpoints (et préfixe) dans un fichier de configuration");
        i2.setMinSize(200,100);
        p2.getContent().add(i2);
        info2.setOnAction(event -> {
            if (!p2.isShowing())
                p2.show(primaryStage);
        });

        Popup p3 = new Popup();
        p3.setAutoHide(true);
        Label i3 = new Label("Démo 3 : \n" +
                "Définition des Endpoints directement dans le code, et pas dans un fichier via une liste " +
                "de Endpoint");
        i3.setMinSize(200,100);
        p3.getContent().add(i3);
        info3.setOnAction(event -> {
            if (!p3.isShowing())
                p3.show(primaryStage);
        });

        Popup p4 = new Popup();
        p4.setAutoHide(true);
        Label i4 = new Label("Démo 4 : \n" +
                "On définit directement les Endpoints en construisant le Repository avec les Endpoints");
        i4.setMinSize(200,100);
        p4.getContent().add(i4);
        info4.setOnAction(event -> {
            if (!p4.isShowing())
                p4.show(primaryStage);
        });

        Popup p5 = new Popup();
        p5.setAutoHide(true);
        Label i5 = new Label("Démo 5 : \n" +
                "Idem Démo 4");
        i5.setMinSize(200,100);
        p5.getContent().add(i5);
        info5.setOnAction(event -> {
            if (!p5.isShowing())
                p5.show(primaryStage);
        });

        Popup p6 = new Popup();
        p6.setAutoHide(true);
        Label i6 = new Label("Démo 6 : \n" +
                "On utilise un fichier de configuration (.prop), dans lequel, en plus de la configuration\n" +
                "est défini un fichier .ttl définissant les Endpoints (idem démo 1 et 2");
        i6.setMinSize(200,100);
        p6.getContent().add(i6);
        info6.setOnAction(event -> {
            if (!p6.isShowing())
                p6.show(primaryStage);
        });

        Popup p7 = new Popup();
        p7.setAutoHide(true);
        Label i7 = new Label("Démo 7 : \n" +
                "Similaire à 6. On utilise en plus un QueryManager qui permet d'ajouter des " +
                "Endpoints avec leur préfixe");
        i7.setMinSize(200,100);
        p7.getContent().add(i7);
        info7.setOnAction(event -> {
            if (!p7.isShowing())
                p7.show(primaryStage);
        });

        VBox vbox2 = new VBox(b, clear);
        vbox2.setPadding(new Insets(10,10,10,0));


        //System.out.println(resultat);

        rowNumber.setPadding(new Insets(10,10,10,10));
        rowNumber.setStyle("-fx-font-weight: bold");
        Label labelResult = new Label("Résultat : ");
        labelResult.setPadding(new Insets(5));
        VBox global = new VBox(radioButton, vbox2,labelResult, sp, rowNumber);
        primaryStage.setScene(new Scene(global,1000,500));
        clear.setOnAction(event -> {
            numberOfRow = 0;
            result.setText(null);
            rowNumber.setText("Clear ! ");
            resultat=null;
        });
        b.setOnAction(event -> {
            numberOfRow = 0;
            result.setText(null);
            rowNumber.setText(null);
            resultat=null;
            if (r1.isSelected()){

                File dataConfig = new File("test/local/test.ttl");

                Config.initialize();
                SailRepository repo = null;
                try {
                    repo = FedXFactory.initializeFederation(dataConfig);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String q = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                        + "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
                        + "SELECT ?President ?Party WHERE {\n"
                        + "?President rdf:type dbpedia-owl:President .\n"
                        + "?President dbpedia-owl:party ?Party . }";

                TupleQuery query = repo.getConnection().prepareTupleQuery(QueryLanguage.SPARQL, q);
                try (TupleQueryResult res = query.evaluate()) {

                    while (res.hasNext()) {
                        resultat += res.next().toString()+"\n";
                        numberOfRow++;
                        //System.out.println(res.next().toString());
                    }
                }
                //System.out.println(resultat);
                result.setText("Démo 1 : \n"+resultat);
                rowNumber.setText("Nombre de résultat : "+numberOfRow);
                //MonitoringUtil.printMonitoringInformation();

                repo.shutDown();
                System.out.println("Done.");
                //System.exit(0);
            }

            else if (r2.isSelected()){


                if (System.getProperty("log4j.configuration")==null)
                    System.setProperty("log4j.configuration", "file:local/log4j.properties");

                File dataConfig = new File("test/tests/localnetwork/LifeScience-FedX-SPARQL.ttl");
                Repository repo = null;
                try {
                    repo = FedXFactory.initializeFederation(dataConfig);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String q = "SELECT ?Drug ?IntDrug ?IntEffect WHERE { "
                        + "?Drug <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://dbpedia.org/ontology/Drug> . "
                        + "?y <http://www.w3.org/2002/07/owl#sameAs> ?Drug . "
                        + "?Int <http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/interactionDrug1> ?y . "
                        + "?Int <http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/interactionDrug2> ?IntDrug . "
                        + "?Int <http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/text> ?IntEffect . }";

                TupleQuery query = repo.getConnection().prepareTupleQuery(QueryLanguage.SPARQL, q);
                try (TupleQueryResult res = query.evaluate()) {

                    while (res.hasNext()) {
                        resultat += res.next().toString()+"\n";
                        numberOfRow++;
                        //System.out.println(res.next());
                    }
                }
                result.setText("Démo 2 : \n"+resultat);
                rowNumber.setText("Nombre de résultat : "+numberOfRow);
                repo.shutDown();
                System.out.println("Done.");
            }

            else if (r3.isSelected()){

                Config.initialize();
                List<Endpoint> endpoints = new ArrayList<Endpoint>();
                endpoints.add( EndpointFactory.loadSPARQLEndpoint("http://dbpedia", "http://dbpedia.org/sparql"));
                //endpoints.add( EndpointFactory.loadSPARQLEndpoint("http://swdf", "http://data.semanticweb.org/sparql"));

                Repository repo = FedXFactory.initializeFederation(endpoints);

                String q = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                        + "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
                        + "SELECT ?President ?Party WHERE {\n"
                        + "?President rdf:type dbpedia-owl:President .\n"
                        + "?President dbpedia-owl:party ?Party . }";

                TupleQuery query = QueryManager.prepareTupleQuery(q);
                try (TupleQueryResult res = query.evaluate()) {

                    while (res.hasNext()) {
                        resultat += res.next().toString()+"\n";
                        numberOfRow++;
                        //System.out.println(res.next());
                    }
                }
                result.setText("Démo 3 : \n"+resultat);
                rowNumber.setText("Nombre de résultat : "+numberOfRow);
                repo.shutDown();
                System.out.println("Done.");
            }

            else if (r4.isSelected()){

                Config.initialize();
                Repository repo = null;
                try {
                    repo = FedXFactory.initializeSparqlFederation(Arrays.asList(
                            "http://dbpedia.org/sparql",
                            "https://query.wikidata.org/sparql"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String q = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                        + "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
                        + "SELECT ?President ?Party WHERE {\n"
                        + "?President rdf:type dbpedia-owl:President .\n"
                        + "?President dbpedia-owl:party ?Party . }";

                TupleQuery query = QueryManager.prepareTupleQuery(q);
                try (TupleQueryResult res = query.evaluate()) {

                    while (res.hasNext()) {
                        resultat += res.next().toString()+"\n";
                        numberOfRow++;
                        //System.out.println(res.next());
                    }
                }
                result.setText("Démo 4 : \n"+resultat);
                rowNumber.setText("Nombre de résultat : "+numberOfRow);
                repo.shutDown();
                System.out.println("Done.");
            }

            else if (r5.isSelected()){

                Config.initialize();
                Repository repo = null;
                try {
                    repo = FedXFactory.initializeSparqlFederation(Arrays.asList(
                            "http://dbpedia.org/sparql"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String q = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                        + "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
                        + "SELECT ?President ?Party WHERE {\n"
                        + "?President rdf:type dbpedia-owl:President .\n"
                        + "?President dbpedia-owl:party ?Party . }";

                TupleQuery query = QueryManager.prepareTupleQuery(q);
                try (TupleQueryResult res = query.evaluate()) {

                    while (res.hasNext()) {
                        resultat += res.next().toString()+"\n";
                        numberOfRow++;
                        //System.out.println(res.next());
                    }
                }
                result.setText("Démo 5 : \n"+resultat);
                rowNumber.setText("Nombre de résultat : "+numberOfRow);
                repo.shutDown();
                System.out.println("Done.");
            }

            else if (r6.isSelected()){

                String fedxConfig = "examples/fedxConfig-withPrefixDecl.prop";
                Config.initialize(fedxConfig);
                Repository repo = FedXFactory.initializeFederation(Collections.<Endpoint>emptyList());

                String q = "SELECT ?President ?Party WHERE {\n"
                        + "?President rdf:type dbpedia:President .\n"
                        + "?President dbpedia:party ?Party . }";

                TupleQuery query = QueryManager.prepareTupleQuery(q);
                try (TupleQueryResult res = query.evaluate()) {

                    while (res.hasNext()) {
                        resultat += res.next().toString()+"\n";
                        numberOfRow++;
                        //System.out.println(res.next());
                    }
                }
                result.setText("Démo 6 : \n"+resultat);
                rowNumber.setText("Nombre de résultat : "+numberOfRow);
                repo.shutDown();
                System.out.println("Done.");
            }

            else if (r7.isSelected()){

                String fedxConfig = "examples/fedxConfig-dataCfg.prop";
                Config.initialize(fedxConfig);
                Repository repo = FedXFactory.initializeFederation(Collections.<Endpoint>emptyList());

                QueryManager qm = FederationManager.getInstance().getQueryManager();
                qm.addPrefixDeclaration("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
                qm.addPrefixDeclaration("dbpedia", "http://dbpedia.org/ontology/");

                String q = "SELECT ?President ?Party WHERE {\n"
                        + "?President rdf:type dbpedia:President .\n"
                        + "?President dbpedia:party ?Party . }";

                TupleQuery query = QueryManager.prepareTupleQuery(q);
                try (TupleQueryResult res = query.evaluate()) {

                    while (res.hasNext()) {
                        resultat += res.next().toString()+"\n";
                        numberOfRow++;
                        //System.out.println(res.next());
                    }
                }
                result.setText("Démo 7 : \n"+resultat);
                rowNumber.setText("Nombre de résultat : "+numberOfRow);
                repo.shutDown();
                System.out.println("Done.");
            }
        });


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
