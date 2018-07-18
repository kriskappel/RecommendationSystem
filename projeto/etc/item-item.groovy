import org.lenskit.transform.normalize.BaselineSubtractingUserVectorNormalizer
import org.lenskit.transform.normalize.UserVectorNormalizer
import org.lenskit.api.ItemScorer
import org.lenskit.baseline.BaselineScorer
import org.lenskit.baseline.ItemMeanRatingItemScorer
import org.lenskit.baseline.UserMeanBaseline
import org.lenskit.baseline.UserMeanItemScorer
import org.lenskit.knn.MinNeighbors
import org.lenskit.knn.item.ItemItemScorer
import org.lenskit.basic.AbstractItemScorer
import org.lenskit.knn.item.ModelSize

//item-item scorer.
bind ItemScorer to ItemItemScorer.class

set MinNeighbors to 2

set ModelSize to 1000

bind (BaselineScorer, ItemScorer) to UserMeanItemScorer

bind (UserMeanBaseline, ItemScorer) to ItemMeanRatingItemScorer

bind UserVectorNormalizer to BaselineSubtractingUserVectorNormalizer
