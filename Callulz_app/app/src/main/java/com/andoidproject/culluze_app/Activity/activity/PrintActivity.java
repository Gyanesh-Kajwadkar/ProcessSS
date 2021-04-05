package com.andoidproject.culluze_app.Activity.activity;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.custumer_area.activity.CheckOut_Activity;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.Manufacture_Activity;
import com.andoidproject.culluze_app.Activity.model.BranchUser_Model;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.CompanyName_Model;
import com.andoidproject.culluze_app.Activity.model.FrequentlyModel;
import com.andoidproject.culluze_app.Activity.network.APIClient;
import com.andoidproject.culluze_app.Activity.network.ApiInterface;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jxl.format.PageOrientation;
import jxl.write.BoldStyle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.pdf.PdfDocument.*;
import static android.view.Gravity.LEFT;
import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.companyAddress;
import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.companyGstn;
import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.companyName;
import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.custmer;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Manufacture_Activity.staticItemList;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity.staticFreqList;

public class PrintActivity extends Base_Activity {

     String sessionId;

     int j=0;
     boolean goBack=false;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        sessionId = getIntent().getStringExtra("totalMoney");
        createPDF("Sales");

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createPDF(String reportName) {
        String pdfFile = reportName + ".pdf";
        String pdf_path = Environment.getExternalStorageDirectory().getPath() + "/Callulz/";
        File directorypdf = new File(pdf_path);
        if (!directorypdf.isDirectory()) {
            directorypdf.mkdirs();
        }
        File fileepdf = new File(directorypdf, pdfFile);
        PdfWriter pdfWriter;
        Document doc = new Document();
        try {
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 0, 0));
            Font bfBoldGray = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(fileepdf));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            Rectangle rect = new Rectangle(30, 30, 550, 800);
            pdfWriter.setBoxSize("art", rect);

            doc.addAuthor("betterThanZero");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("MySampleCode.com");
            doc.addTitle("Report with Column Headings");
            doc.setPageSize(PageSize.LETTER);
            doc.open();
            float[] reportcolumnWidths = {0.6f, 0.1f, 2f};
            PdfPTable reportTable = new PdfPTable(reportcolumnWidths);
            reportTable.setWidthPercentage(100f);

            Paragraph paragraph = new Paragraph();
            Paragraph paragraph2 = new Paragraph();
            float[] columnWidths = {0.5f, 2f, 1f, 1f, 1f,1f};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100f);

            Paragraph paragraph3 = new Paragraph();
            float[] columnWidths3 = {0.5f, 2f, 0.5f, 1.5f, 1f};
            PdfPTable table3 = new PdfPTable(columnWidths3);
            table3.setWidthPercentage(100f);

            Paragraph paragraph4 = new Paragraph();
            float[] columnWidths4 = {1f, 2f, 0.3f, 1f, 1f,0.5f,2f,2f};
            PdfPTable table4 = new PdfPTable(columnWidths4);
            table4.setWidthPercentage(100f);

            String name = "_",address = "_",gstn = "_";
            ArrayList<BranchUser_Model>branch=getHistory("branchUser");
            Log.e("users",branch.size()+"");
            for(int i=0;i<branch.size();i++)
            {
                BranchUser_Model users=branch.get(i);
                try
                {
                name=users.getBrnchName();
                address=users.getAddress();
                gstn=users.getGstin();
                }
                catch (Exception e)
                {
                    Log.e("exce",e.getMessage());
                }

            }

            insertStringCell(reportTable, "Company Name", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, ":-", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, companyName, Element.ALIGN_LEFT, 1, bfBold12);

            insertStringCell(reportTable, "Address", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, ":-", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, companyAddress, Element.ALIGN_LEFT, 1, bfBold12);

            insertStringCell(reportTable, "GSTN", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, ":-", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, companyGstn+"", Element.ALIGN_LEFT, 1, bfBold12);

            insertStringCell(reportTable, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, "", Element.ALIGN_LEFT, 1, bfBold12);


            insertStringCell(reportTable, "Customer Name", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, ":-", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, custmer.getCustmrName(), Element.ALIGN_LEFT, 1, bfBold12);

            insertStringCell(reportTable, "Address", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, ":-", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, custmer.getAddress(), Element.ALIGN_LEFT, 1, bfBold12);

            insertStringCell(reportTable, "GSTN", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, ":-", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, custmer.getGstin()+"", Element.ALIGN_LEFT, 1, bfBold12);


           /* insertStringCell(reportTable, "Party Name", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, ":-", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, name, Element.ALIGN_LEFT, 1, bfBold12);

            insertStringCell(reportTable, "Address", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, ":-", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, address, Element.ALIGN_LEFT, 1, bfBold12);

            insertStringCell(reportTable, "GSTN", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, ":-", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, gstn, Element.ALIGN_LEFT, 1, bfBold12);*/


            insertStringCell(reportTable, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(reportTable, "", Element.ALIGN_LEFT, 1, bfBold12);

            
            DateFormat df = new SimpleDateFormat( "d MMM yyyy");
            DateFormat dfTime = new SimpleDateFormat( "h:mm a");
            String date = df.format(Calendar.getInstance().getTime());
            String time = dfTime.format(Calendar.getInstance().getTime());


            insertStringCell(table4, "Date:-", Element.ALIGN_CENTER, 1, bfBold12);
            insertStringCell(table4, date, Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_CENTER, 1, bfBold12);
            insertStringCell(table4, "Time:-", Element.ALIGN_CENTER, 1, bfBold12);
            insertStringCell(table4, time, Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_CENTER, 1, bfBold12);
            insertStringCell(table4, "Login User:-", Element.ALIGN_CENTER, 1, bfBold12);
            insertStringCell(table4, loginModel.getUsrName(), Element.ALIGN_LEFT, 1, bfBold12);

            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
           /* insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertStringCell(table4, "", Element.ALIGN_LEFT, 1, bfBold12);
*/

            insertCell(table, "Sl No", Element.ALIGN_CENTER, 1, bf12);
            insertCell(table, "Item Name", Element.ALIGN_CENTER, 1, bf12);
            insertCell(table, "Quantity", Element.ALIGN_CENTER, 1, bf12);
            insertCell(table, "Tax", Element.ALIGN_CENTER, 1, bf12);
            insertCell(table, "Rate", Element.ALIGN_CENTER, 1, bf12);
            insertCell(table, "Amount", Element.ALIGN_CENTER, 1, bf12);
            // insertCell(table, "F", Element.ALIGN_CENTER, 1, bf12);

                /*    for(int i=0;i<staticFreqList.size();i++)
                    {*/


            float total = 0;
            String rate = "";
            for(int i=0;i<staticFreqList.size();i++)
            {
                FrequentlyModel model=staticFreqList.get(i);
                j=i+1;
                insertContent(table,String.valueOf(j), 1, bf12);
                insertContent(table, model.getItmName(), 1, bf12);
                insertContent(table, String.valueOf(model.getQuantity()), 1, bf12);
                insertContent(table, String.valueOf(model.getTaxPerc()), 1, bf12);
                String customerRate=custmer.getCustRate();

                if(customerRate.equals("ITM-RT"))
                {
                    total=model.getQuantity()* model.getRTSWT();
                    rate= String.valueOf(model.getRTSWT());

                }
                else if(customerRate.equals("ITM-WH"))
                {
                    total=model.getQuantity()* model.getWHSWT();
                    rate= String.valueOf(model.getWHSWT());
                }
                else if(customerRate.equals("ITM-DI"))
                {
                    total=model.getQuantity()* model.getDTSWT();
                    rate= String.valueOf(model.getDTSWT());

                }
                else if(customerRate.equals("ITM-EX"))
                {
                    total=model.getQuantity()* model.getEXSWT();
                    rate= String.valueOf(model.getEXSWT());
                }

                insertContent(table, rate, 1, bf12);
                insertContent(table, String.valueOf(total), 1, bf12);
            }



            float totall = 0;
            String ratee = "";
            for(int i=0;i<staticItemList.size();i++)
            {
                Brand_SubBrand_Model.Item model=staticItemList.get(i);
                j=j+1;

                insertContent(table,String.valueOf(j), 1, bf12);
                insertContent(table, model.getItmName(), 1, bf12);
                insertContent(table, String.valueOf(model.getQuantity()), 1, bf12);
                insertContent(table, String.valueOf(model.getTaxPerc()), 1, bf12);

                String customerRate=custmer.getCustRate();

                if(customerRate.equals("ITM-RT"))
                {
                    totall=model.getQuantity()* model.getRTSWT();
                    ratee= String.valueOf(model.getRTSWT());
                }
                else if(customerRate.equals("ITM-WH"))
                {
                    totall=model.getQuantity()* model.getWHSWT();
                    ratee= String.valueOf(model.getWHSWT());
                }
                else if(customerRate.equals("ITM-DI"))
                {
                    totall=model.getQuantity()* model.getDTSWT();
                    ratee= String.valueOf(model.getDTSWT());
                }
                else if(customerRate.equals("ITM-EX"))
                {
                    totall=model.getQuantity()* model.getEXSWT();
                    ratee= String.valueOf(model.getEXSWT());
                }
                insertContent(table, ratee, 1, bf12);
                insertContent(table, String.valueOf(totall), 1, bf12);
            }


      /*      insertContent(table, "2", 1, bf12);
            insertContent(table, "xyz", 1, bf12);
            insertContent(table, "3", 1, bf12);
            insertContent(table, "10", 1, bf12);
            insertContent(table, "50", 1, bf12);

            insertContent(table, "3", 1, bf12);
            insertContent(table, "xyz", 1, bf12);
            insertContent(table, "3", 1, bf12);
            insertContent(table, "10", 1, bf12);
            insertContent(table, "50", 1, bf12);
            insertContent(table, "4", 1, bf12);
            insertContent(table, "xyz", 1, bf12);
            insertContent(table, "3", 1, bf12);
            insertContent(table, "10", 1, bf12);
            insertContent(table, "50", 1, bf12);
            insertContent(table, "5", 1, bf12);
            insertContent(table, "xyz", 1, bf12);
            insertContent(table, "3", 1, bf12);
            insertContent(table, "10", 1, bf12);
            insertContent(table, "50", 1, bf12);
*/

            insertBottom(table, "", 1, bf12);
            insertBottom(table, "", 1, bf12);
            insertBottom(table, "", 1, bf12);
            insertBottom(table, "", 1, bf12);
            insertBottom(table, "", 1, bf12);
            insertBottom(table, "", 1, bf12);


            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, "CGST", Element.ALIGN_RIGHT, 1, bf12);
            insertStringCell(table3, "174.1", Element.ALIGN_CENTER, 1, bf12);

            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, "SGST", Element.ALIGN_RIGHT, 1, bf12);
            insertStringCell(table3, "174.1", Element.ALIGN_CENTER, 1, bf12);

            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, "Kerala Flood Cess", Element.ALIGN_RIGHT, 1, bf12);
            insertStringCell(table3, custmer.getAvilBal()+"", Element.ALIGN_CENTER, 1, bf12);

            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, "ROUND OFF", Element.ALIGN_RIGHT, 1, bf12);
            insertStringCell(table3, "0", Element.ALIGN_CENTER, 1, bf12);

            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, " ", Element.ALIGN_CENTER, 1, bf12);
            insertStringCell(table3, "TOTAL AMOUNT", Element.ALIGN_RIGHT, 1, bfBoldGray);
            insertStringCell(table3, sessionId, Element.ALIGN_CENTER, 1, bfBoldGray);

        /*    HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            pdfWriter.setPageEvent(event);*/

            paragraph.add(reportTable);
            paragraph2.add(table);
            paragraph3.add(table3);
            paragraph4.add(table4);
            // paragraph3.add(table3);

            // add the paragraph to the document
            //doc.add(reportParagraph);
            doc.add(paragraph);
            doc.add(paragraph4);
            doc.add(paragraph2);
            doc.add(paragraph3);

            // doc.add(paragraph3);
        }
        catch (DocumentException dex)
        {
            dex.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (doc != null){
                //close the document
                doc.close();
            }
            /*if (docWriter != null){
                //close the writer
                docWriter.close();
            }*/
        }
     /*   startActivity(new Intent(PrintActivity.this,OpenPrinterActivity.class)
                .putExtra("fileepdf",fileepdf));*/

        openPrintMethod(fileepdf);

        // open Printer
          /*  initPrintDialog();
            mPrintDialog.show();
            mObservable.attach((Observer) PrintReportActivity.this);
            mPrintUtility.print(fileepdf);*/
    }



    private void insertStringCell( PdfPTable table,String text, int align, int colspan, Font font){
        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);

            cell.setMinimumHeight(10f);

        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        cell.setVerticalAlignment(align);
        cell.setPaddingBottom(5);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        table.addCell(cell);
    }

    private void insertContent(PdfPTable table, String text,int colspan,Font font)
    {
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(5);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderWidthRight(1);
        cell.setBorderWidthLeft(1);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthBottom(0);
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        table.addCell(cell);
    }

    private void insertBottom(PdfPTable table, String text,int colspan,Font font)
    {
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(5);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthTop(1);
        cell.setBorderWidthBottom(0);
        cell.setMinimumHeight(1f);
        table.addCell(cell);
    }

    public class HeaderFooterPageEvent extends PdfPageEventHelper {
        public void onStartPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Left"), rect.getLeft(), rect.getTop(), 0);
            ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Right"), rect.getRight(), rect.getTop(), 0);
        }
        public void onEndPage(PdfWriter writer,Document document) {
            Rectangle rect = writer.getBoxSize("art");
            ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("@2019 SmartPro Technologies. All rights reserved."),  rect.getRight() / 2, rect.getBottom(), 0);
            // ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Bottom Right"), rect.getRight(), rect.getBottom(), 0);
        }}

    public ArrayList<BranchUser_Model> getHistory(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<BranchUser_Model>>() {}.getType();
        return gson.fromJson(json, type);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(goBack)
        {
            staticItemList.clear();
            saveToSpBrandItem(staticItemList);
            staticFreqList.clear();
            saveToSpFreqItem(staticFreqList);
            onBackPressed();
            Intent intent=new Intent(PrintActivity.this, Manufacture_Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void openPrintMethod(File filePath)
    {
        if (filePath!=null) {
            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
            try
            {
                PrintDocumentAdapter printAdapter = new PdfDocumentAdapter(PrintActivity.this, filePath);
                printManager.print("Document", printAdapter, new PrintAttributes.Builder().build());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public class PdfDocumentAdapter extends PrintDocumentAdapter {
        Context context = null;
        File pathName;
        public PdfDocumentAdapter(Context ctxt, File pathName) {
            context = ctxt;
            this.pathName = pathName;
        }

        @Override
        public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes1, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
            if (cancellationSignal.isCanceled()) {
                layoutResultCallback.onLayoutCancelled();
            }
            else {
                PrintDocumentInfo.Builder builder=
                        new PrintDocumentInfo.Builder(" file name");
                builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                        .build();
                layoutResultCallback.onLayoutFinished(builder.build(),
                        !printAttributes1.equals(printAttributes));
            }
        }



        @Override
        public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
            InputStream in=null;
            OutputStream out=null;
            try {
                goBack=true;
                //File file = new File(String.valueOf(pathName));
                in = new FileInputStream(pathName);
                out=new FileOutputStream(parcelFileDescriptor.getFileDescriptor());

                byte[] buf=new byte[16384];
                int size;
                while ((size=in.read(buf)) >= 0
                        && !cancellationSignal.isCanceled()) {
                    out.write(buf, 0, size);
                }

                if (cancellationSignal.isCanceled()) {
                    writeResultCallback.onWriteCancelled();
                }
                else {
                    writeResultCallback.onWriteFinished(new PageRange[] { PageRange.ALL_PAGES });
                }
            }
            catch (Exception e) {
                writeResultCallback.onWriteFailed(e.getMessage());
                e.printStackTrace();
            }
            finally {
                try {
                    in.close();
                    out.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                } }}}


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();}






}
