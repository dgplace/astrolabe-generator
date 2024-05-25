package com.wymarc.astrolabe.generator.printengines.postscript.extras.laserFiles;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;

public class LatitudeRing {

    /**
     * Computes the markings for the back bezel
     *
     * @return  returns the ps code for drawing the Degree Scale
     *
     */
    private String buildBackLimb(Astrolabe myAstrolabe){
        double outerRadius = myAstrolabe.getMaterRadius();
        int count;
        String out = "";

        out += "\n" + "% ==================== Create Back ====================";
        out += "\n" + "% Draw outer circle";
        out += "\n" + "1 0 0 setrgbcolor"; //cutting line
        out += "\n" + "0 0 " + (outerRadius + 5) + " 0 360 arc stroke";

        out += "\n" + "0 0 1 setrgbcolor";
        out += "\n" + "0 0 " + outerRadius + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius -2) + " 0 360 arc stroke";

        out += "\n" + "0 0 " + (outerRadius - 7) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 9) + " 0 360 arc stroke";

        out += "\n" + "0 0 " + (outerRadius - 24) + " 0 360 arc stroke";

        out += "\n" + "0 0 " + (outerRadius - 39) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 41) + " 0 360 arc stroke";

        out += "\n" + "-180 rotate";

        // create degree marks
        for (count = 0; count <= 180; count++){
            String countString = count+"";
            if (countString.endsWith("0")){ // tens
                out += "\n" + (outerRadius - 39) + " 0 moveto";
                out += "\n" + (outerRadius - 2) + " 0 lineto stroke";
            }else if(countString.endsWith("5")){
                out += "\n" + (outerRadius - 24) + " 0 moveto";
                out += "\n" + (outerRadius - 2) + " 0 lineto stroke";
            }else{
                out += "\n" + (outerRadius - 7) + " 0 moveto";
                out += "\n" + (outerRadius - 2) + " 0 lineto stroke";
            }
            out += "\n" + "1 rotate";
        }

        out += "\n" + "180 rotate";

        out += "\n" + "0 setgray";
        //Mark degrees
        out += "\n" + "ArialFont14 setfont";
        for (count = 9; count >= 0; count--){
            out += EPSToolKit.drawOutsideCircularText(Integer.toString((9 - count)*10), 14, ((count*10)-1), (outerRadius - 36));
        }
        for (count = 8; count >= 0; count--){
            out += EPSToolKit.drawOutsideCircularText(Integer.toString((9 - count)*10), 14, (180-(count*10)-1), (outerRadius - 36));
        }
        for (count = 5; count < 180; count = count + 10){
            out += EPSToolKit.drawOutsideCircularText("5", 14, (count-1), (outerRadius - 21));
        }

        out += "\n" + "%% ==================== End Create Back ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Draws the The face of a latitude ring
     * Note: not part of the generator, used once
     *
     * @return  returns the ps code for drawing the latitude ring
     *
     *
     */
    public String create(Astrolabe myAstrolabe){

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Latitude Ring");
        out += "\n" + "%% setup";
        out += "\n" + "306 396 translate";
        out += "\n" + ".1 setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpCircularText();

        out += "\n" + "gsave";
        out += buildBackLimb(myAstrolabe);
        out += "\n" + "grestore";
        out += "\n" + "";

        // mark pivot point
        out += "\n" + "%% Mark pivot";
        out += "\n" + "newpath";
        out += "\n" + "1 0 0 setrgbcolor";
        out += "\n" + "0 0 5 0 360 arc stroke";
        out += "\n" + "";

        // Write Footer
        out += "\n" + "% Eject the page";
        out += "\n" + "end cleartomark";
        out += "\n" + "showpage";

        return out;
    }
}
