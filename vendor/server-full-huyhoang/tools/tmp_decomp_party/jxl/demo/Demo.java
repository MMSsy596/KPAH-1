/*
 * Decompiled with CFR 0.152.
 */
package jxl.demo;

import common.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import jxl.Cell;
import jxl.Range;
import jxl.Workbook;
import jxl.demo.BiffDump;
import jxl.demo.CSV;
import jxl.demo.Escher;
import jxl.demo.EscherDrawingGroup;
import jxl.demo.Features;
import jxl.demo.Formulas;
import jxl.demo.PropertySetsReader;
import jxl.demo.ReadWrite;
import jxl.demo.Write;
import jxl.demo.WriteAccess;
import jxl.demo.XML;

public class Demo {
    private static final int CSVFormat = 13;
    private static final int XMLFormat = 14;
    private static Logger logger = Logger.getLogger(class$jxl$demo$Demo == null ? (class$jxl$demo$Demo = Demo.class$("jxl.demo.Demo")) : class$jxl$demo$Demo);
    static /* synthetic */ Class class$jxl$demo$Demo;

    private static void displayHelp() {
        System.err.println("Command format:  Demo [-unicode] [-csv] [-hide] excelfile");
        System.err.println("                 Demo -xml [-format]  excelfile");
        System.err.println("                 Demo -readwrite|-rw excelfile output");
        System.err.println("                 Demo -biffdump | -bd | -wa | -write | -formulas | -features | -escher | -escherdg excelfile");
        System.err.println("                 Demo -ps excelfile [property] [output]");
        System.err.println("                 Demo -version | -logtest | -h | -help");
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            Demo.displayHelp();
            System.exit(1);
        }
        if (args[0].equals("-help") || args[0].equals("-h")) {
            Demo.displayHelp();
            System.exit(1);
        }
        if (args[0].equals("-version")) {
            System.out.println("v" + Workbook.getVersion());
            System.exit(0);
        }
        if (args[0].equals("-logtest")) {
            logger.debug("A sample \"debug\" message");
            logger.info("A sample \"info\" message");
            logger.warn("A sample \"warning\" message");
            logger.error("A sample \"error\" message");
            logger.fatal("A sample \"fatal\" message");
            System.exit(0);
        }
        boolean write = false;
        boolean readwrite = false;
        boolean formulas = false;
        boolean biffdump = false;
        boolean jxlversion = false;
        boolean propertysets = false;
        boolean features = false;
        boolean escher = false;
        boolean escherdg = false;
        String file = args[0];
        String outputFile = null;
        String propertySet = null;
        if (args[0].equals("-write")) {
            write = true;
            file = args[1];
        } else if (args[0].equals("-formulas")) {
            formulas = true;
            file = args[1];
        } else if (args[0].equals("-features")) {
            features = true;
            file = args[1];
        } else if (args[0].equals("-escher")) {
            escher = true;
            file = args[1];
        } else if (args[0].equals("-escherdg")) {
            escherdg = true;
            file = args[1];
        } else if (args[0].equals("-biffdump") || args[0].equals("-bd")) {
            biffdump = true;
            file = args[1];
        } else if (args[0].equals("-wa")) {
            jxlversion = true;
            file = args[1];
        } else if (args[0].equals("-ps")) {
            propertysets = true;
            file = args[1];
            if (args.length > 2) {
                propertySet = args[2];
            }
            if (args.length == 4) {
                outputFile = args[3];
            }
        } else if (args[0].equals("-readwrite") || args[0].equals("-rw")) {
            readwrite = true;
            file = args[1];
            outputFile = args[2];
        } else {
            file = args[args.length - 1];
        }
        String encoding = "UTF8";
        int format = 13;
        boolean formatInfo = false;
        boolean hideCells = false;
        if (!(write || readwrite || formulas || biffdump || jxlversion || propertysets || features || escher || escherdg)) {
            for (int i = 0; i < args.length - 1; ++i) {
                if (args[i].equals("-unicode")) {
                    encoding = "UnicodeBig";
                    continue;
                }
                if (args[i].equals("-xml")) {
                    format = 14;
                    continue;
                }
                if (args[i].equals("-csv")) {
                    format = 13;
                    continue;
                }
                if (args[i].equals("-format")) {
                    formatInfo = true;
                    continue;
                }
                if (args[i].equals("-hide")) {
                    hideCells = true;
                    continue;
                }
                System.err.println("Command format:  CSV [-unicode] [-xml|-csv] excelfile");
                System.exit(1);
            }
        }
        try {
            if (write) {
                Write w = new Write(file);
                w.write();
            } else if (readwrite) {
                ReadWrite rw = new ReadWrite(file, outputFile);
                rw.readWrite();
            } else if (formulas) {
                Workbook w = Workbook.getWorkbook(new File(file));
                Formulas f = new Formulas(w, System.out, encoding);
                w.close();
            } else if (features) {
                Workbook w = Workbook.getWorkbook(new File(file));
                Features f = new Features(w, System.out, encoding);
                w.close();
            } else if (escher) {
                Workbook w = Workbook.getWorkbook(new File(file));
                Escher f = new Escher(w, System.out, encoding);
                w.close();
            } else if (escherdg) {
                Workbook w = Workbook.getWorkbook(new File(file));
                EscherDrawingGroup f = new EscherDrawingGroup(w, System.out, encoding);
                w.close();
            } else if (biffdump) {
                BiffDump bd = new BiffDump(new File(file), System.out);
            } else if (jxlversion) {
                WriteAccess bd = new WriteAccess(new File(file));
            } else if (propertysets) {
                OutputStream os = System.out;
                if (outputFile != null) {
                    os = new FileOutputStream(outputFile);
                }
                PropertySetsReader psr = new PropertySetsReader(new File(file), propertySet, os);
            } else {
                Workbook w = Workbook.getWorkbook(new File(file));
                if (format == 13) {
                    CSV csv = new CSV(w, System.out, encoding, hideCells);
                } else if (format == 14) {
                    XML xml = new XML(w, System.out, encoding, formatInfo);
                }
                w.close();
            }
        }
        catch (Throwable t) {
            System.out.println(t.toString());
            t.printStackTrace();
        }
    }

    private static void findTest(Workbook w) {
        int i;
        Range[] range;
        logger.info("Find test");
        Cell c = w.findCellByName("named1");
        if (c != null) {
            logger.info("named1 contents:  " + c.getContents());
        }
        if ((c = w.findCellByName("named2")) != null) {
            logger.info("named2 contents:  " + c.getContents());
        }
        if ((c = w.findCellByName("namedrange")) != null) {
            logger.info("named2 contents:  " + c.getContents());
        }
        if ((range = w.findByName("namedrange")) != null) {
            c = range[0].getTopLeft();
            logger.info("namedrange top left contents:  " + c.getContents());
            c = range[0].getBottomRight();
            logger.info("namedrange bottom right contents:  " + c.getContents());
        }
        if ((range = w.findByName("nonadjacentrange")) != null) {
            for (i = 0; i < range.length; ++i) {
                c = range[i].getTopLeft();
                logger.info("nonadjacent top left contents:  " + c.getContents());
                c = range[i].getBottomRight();
                logger.info("nonadjacent bottom right contents:  " + c.getContents());
            }
        }
        if ((range = w.findByName("horizontalnonadjacentrange")) != null) {
            for (i = 0; i < range.length; ++i) {
                c = range[i].getTopLeft();
                logger.info("horizontalnonadjacent top left contents:  " + c.getContents());
                c = range[i].getBottomRight();
                logger.info("horizontalnonadjacent bottom right contents:  " + c.getContents());
            }
        }
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

