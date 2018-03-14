package threeD.trueshot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.python.util.PythonInterpreter;

public class JythonTest extends TestCase
{
	PythonInterpreter pythonInterpreter;

	public JythonTest(String name)
	{
		super(name);
	}

	public static Test suites()
	{
		return new TestSuite(JythonTest.class);
	}

	public void testJythonImport()
	{
		String directory = System.getProperty("user.dir");
		System.out.println(directory);
		pythonInterpreter = new PythonInterpreter();
		pythonInterpreter.exec("import sys");
		pythonInterpreter.exec("sys.path.insert(0, \"S:\\TrueShot\\src\\scripts\")");
		pythonInterpreter.exec("print(sys.path)");
		pythonInterpreter.exec("import testy");
	}

	public void testJythonExecFile()
	{
		pythonInterpreter = new PythonInterpreter();
		pythonInterpreter.execfile("src/test/java/threeD/trueshot/testy.py");
	}

}
