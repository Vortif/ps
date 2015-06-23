package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swt.FXCanvas;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

/**
 * Created by Maciej Chrzan on 2015-04-04.
 */
public class Filters {
    private static ImageView imageView;
    private static double[][] imgG;
    private static double[][] imgR;
    private static double[][] imgB;
    private static Image image;
    private static double angle=0;
    private Filters(){

    }
    public static void setImageView(ImageView iv){
        imageView=iv;
        angle=imageView.getRotate();
        imageView.setRotate(0);
        image = SwingFXUtils.toFXImage(SwingFXUtils.fromFXImage(imageView.snapshot(null, null), null),null);
    }

    private static void setRGBMatrix(){
        int height= (int)image.getHeight();
        int width = (int)image.getWidth();
        imgR=new double[width][height];
        imgG=new double[width][height];
        imgB=new double[width][height];
        PixelReader reader = image.getPixelReader();
        for(int readY=0;readY<height;readY++){
            for(int readX=0;readX<width;readX++){
                Color color = reader.getColor(readX,readY);
                imgG[readX][readY]=color.getGreen();
                imgB[readX][readY]=color.getBlue();
                imgR[readX][readY]=color.getRed();
            }
        }
    }
    private static Image getImage(){
        BufferedImage image = SwingFXUtils.fromFXImage(imageView.snapshot(null, null), null);
        return SwingFXUtils.toFXImage(image,null);
    }
    private static int[][] getMask(int weight,int size){
        int [][] mask = new int[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                mask[i][j]=weight;
            }
        }
        return  mask;
    }
    private static void initializeRGBMATRIX(int height,int width){
        imgR=new double[width][height];
        imgB=new double[width][height];
        imgG=new double[width][height];
    }
    public static void filtering(int[][] mask){//blur
        int height= (int)image.getHeight();
        int width = (int)image.getWidth();
        initializeRGBMATRIX(height, width);
        setRGBMatrix();
        imgR=countSum(mask,imgR);
        imgB=countSum(mask,imgB);
        imgG=countSum(mask,imgG);
        WritableImage wi=new WritableImage(width,height);
        PixelWriter pw = wi.getPixelWriter();
        writePixels(pw,imgR,imgB,imgG,height,width);
        imageView.setRotate(angle);
        imageView.setImage(wi);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
    }
    public static void averagingFilter(){//blur
        filtering(getMask(1,3));
    }
    private static int[][] getGaussMask(){
        int [][] mask= new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if((i==0||i==2)&&(j==0||j==2)){
                    mask[i][j]=1;
                }
                else{
                    mask[i][j]=2;
                }
            }
            mask[1][1]=4;
        }
        return mask;
    }
    public static void gaussFilter(){
        filtering(getGaussMask());
    }
    private static int[][] getEdgesMask(){//gradient directional east
        int [][] mask=new int[3][3];//
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(j==0)mask[i][j]=-1;
                else mask[i][j]=1;
            }
        }
        mask[1][1]=-2;
        return mask;
    }
    private static int[][] getSharpenMask(){
        int [][] mask=new int[3][3];//hp3 filter
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if((i+j)%2==1) mask[i][j]=-1;
                else mask[i][j]=0;
            }
        }
        mask[1][1]=20;
        return mask;
    }
    public static void sharpen(){
        filtering(getSharpenMask());
    }
    public static void showEdges(){
        filtering(getEdgesMask());
    }
    private static void writePixels(PixelWriter pixelWriter,double[][] matrixR,double[][] matrixB,double[][] matrixG,int height,int width){
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                pixelWriter.setColor(i,j,Color.color(imgR[i][j],(imgG[i][j]),((imgB[i][j]))));
            }
        }
    }
    private static double[][] countSum(int[][] mask,double[][]matrix){
        double[][] toRet=new double[matrix.length][matrix.length>0?matrix[0].length:0];
        int margin = (mask.length-1)/2;
        for (int i=margin;i<matrix.length-margin;i++){
            for (int j=margin;j<toRet[0].length-margin;j++){
                double sum=0;
                int norm=0;
                for(int k=0;k<mask.length;k++){
                    for(int l=0;l<mask.length;l++){
                        sum+=mask[k][l]*matrix[i+k-margin][j+l-margin];
                        norm+=mask[k][l];
                    }
                }
                sum=sum/norm;
                toRet[i][j]=sum>1?1:(sum<0?0:sum);
            }
        }
        return toRet;
    }
    private static int[][] getEmbossingMask(){
        int[][] mask = new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(j==0) mask[i][j]=-1;
                else if(j==2) mask[i][j]=1;
                else mask[i][j]=0;
            }
            mask[1][1]=1;
        }
        return mask;
    }
    public static void embossing(){//uwypuklanie wschodnie (east embossing)
        filtering(getEmbossingMask());
    }
    private static int[][] laplaceMask(){
        int[][] mask=new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if((i+j)%2==1) mask[i][j]=-1;
                else mask[i][j]=0;
            }
            mask[1][1]=4;
        }
        return mask;
    }
    public static void laplace(){
        filtering(laplaceMask());
    }
}
