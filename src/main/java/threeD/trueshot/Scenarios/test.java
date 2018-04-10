package threeD.trueshot.Scenarios;

public class test {

    public static void main(String args[]){

        singleShot01 test = new singleShot01(2.5, 3, 0, "res/sound/test/c2.wav");

        byte[] convolvedByteArray = test.getConvolvedByteArray();
        test.play();

    }

}
