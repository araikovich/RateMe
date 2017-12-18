package araikovichinc.ratemeconcept2.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import static com.bumptech.glide.load.resource.bitmap.BitmapEncoder.COMPRESSION_QUALITY;

/**
 * Created by Tigran on 03.12.2017.
 */

public class BitmapHelper {
    public static Bitmap byteToBitmap(byte[] data){
        return BitmapFactory.decodeByteArray(data , 0, data.length);
    }

    public static Bitmap rotate(Bitmap in, int angle) {
        Matrix mat = new Matrix();
        mat.postRotate(angle);
        return Bitmap.createBitmap(in, 0, 0, in.getWidth(), in.getHeight(), mat, true);
    }

    public static String bitmapToBase64(Bitmap picture){
        String reult = null;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        try {
            picture.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayBitmapStream);
            byte[] b = byteArrayBitmapStream.toByteArray();
            reult =  Base64.encodeToString(b, Base64.NO_WRAP);
        }catch (Exception e){
            e.printStackTrace();
        }catch (OutOfMemoryError e){
            picture.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayBitmapStream);
            byte[] b = byteArrayBitmapStream.toByteArray();
            reult = Base64.encodeToString(b, Base64.NO_WRAP);
        }
        return reult;
    }

    public static Bitmap getBitmapFromPath(String filePath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = null;
        for(options.inSampleSize = 1; options.inSampleSize<=32; options.inSampleSize++){
            try{
                bitmap = BitmapFactory.decodeFile(filePath, options);
                break;
            }catch (OutOfMemoryError e){
                e.getMessage();
            }
        }
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Реальные размеры изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Вычисляем наибольший inSampleSize, который будет кратным двум
            // и оставит полученные размеры больше, чем требуемые
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
