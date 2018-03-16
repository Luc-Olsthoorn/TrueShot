package threeD.trueshot.lib.hrtf;

/**
 * Allows a user to access the subject for the respective HRTF database.
 */
public class Hrtf
{
    public static CipicSubject getCipicSubject(String subjectNumber)
    {
        return new CipicSubject(subjectNumber);
    }
}
