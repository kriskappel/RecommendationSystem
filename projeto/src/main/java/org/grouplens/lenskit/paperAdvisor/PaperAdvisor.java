package org.grouplens.lenskit.paperAdvisor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.lenskit.LenskitConfiguration;
import org.lenskit.LenskitRecommender;
import org.lenskit.LenskitRecommenderEngine;
import org.lenskit.api.ItemRecommender;
import org.lenskit.api.Result;
import org.lenskit.api.ResultList;
import org.lenskit.config.ConfigHelpers;
import org.lenskit.data.dao.DataAccessObject;
import org.lenskit.data.dao.file.StaticDataSource;
import org.lenskit.data.entities.CommonAttributes;
import org.lenskit.data.entities.CommonTypes;
import org.lenskit.data.entities.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;


public class PaperAdvisor implements Runnable {

	//main
	public static void main(String[] args) {
        PaperAdvisor paperadvisor = new PaperAdvisor(args);
      
        paperadvisor.run();
    }

	//private variables
	private List<Long> userIds = new ArrayList<>();
    private List<Long> userNRec = new ArrayList<>();

	private static final Logger printLogs = LoggerFactory.getLogger(PaperAdvisor.class);

    private Path path = Paths.get("data/movielens.yml");
    
    //public methods

    public PaperAdvisor(String[] args) {
        Boolean flag=true;
        for (String id: args) {
            if(flag) {
                userIds.add(Long.parseLong(id));
                flag = !flag;
            }
            else {
                userNRec.add(Long.parseLong(id));
                flag = !flag;
            }

        }            
    }

     public void run() {

     	//lenskit config
     	LenskitConfiguration configLenskit = null;
     	File groovy = new File("etc/user-item.groovy");

        try {
            configLenskit = ConfigHelpers.load(groovy);
        } catch (IOException e) {
            throw new RuntimeException("LenskitConfiguration", e);
        }

     	//get data from files
        DataAccessObject dao;

        try {
            StaticDataSource data = StaticDataSource.load(path);
            
            dao = data.get();
        } catch (IOException e) {
            printLogs.error("data", e);
            throw Throwables.propagate(e);
        }


        LenskitRecommenderEngine engineLenskit = LenskitRecommenderEngine.build(configLenskit, dao);
       	LenskitRecommender recommender = engineLenskit.createRecommender(dao);
        printLogs.info("recommender created");
            
        ItemRecommender itemRec = recommender.getItemRecommender();
        assert itemRec != null; //recommender configured -> !=null
       
       	//print recommendations
        int userNumber = 0;
        for (long id : userIds) { //for each user
            
            ResultList recommendations = itemRec.recommendWithDetails(id, userNRec.get(userNumber).intValue(), null, null);
            System.out.println("\nPapers recommended to user  " +id+ " :\n");

            for (int i = 0 ; i < userNRec.get(userNumber) ; i++) { //recommend papers

            	Result paper = recommendations.get(i);

                Entity entityPaper = dao.lookupEntity(CommonTypes.ITEM, paper.getId());
                String namePaper = "";

                if (entityPaper != null) {
                    namePaper = entityPaper.maybeGet(CommonAttributes.NAME);
                }

                //System.out.print("\t" + i + "- Title = " + namePaper + " | id = " + paper.getId() + " | score = " +paper.getScore());
                //System.out.printf("%.2f\n", paper.getScore());

                System.out.format("\t %d - Title = %s | id = %d | score = %.2f\n", (i+1), namePaper, paper.getId(), paper.getScore());

            }
            userNumber++;
        }
    }
}

   