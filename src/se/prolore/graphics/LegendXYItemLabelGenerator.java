package se.prolore.graphics;

import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.labels.BubbleXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.PublicCloneable;

import java.io.Serializable;

/**
 * Created by matseriksson on 29/10/14.
 */
public class LegendXYItemLabelGenerator extends BubbleXYItemLabelGenerator
        implements XYItemLabelGenerator, Cloneable, PublicCloneable,
        Serializable {
    private LegendItemCollection legendItems;

    public LegendXYItemLabelGenerator(LegendItemCollection legendItems) {
        super();
        this.legendItems = legendItems;
    }

    @Override
    public String generateLabel(XYDataset dataset, int series, int item) {
        LegendItem legendItem = legendItems.get(series);

        return legendItem.getDescription();
        //return "";
    }
}