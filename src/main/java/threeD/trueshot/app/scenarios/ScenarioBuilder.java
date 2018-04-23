package threeD.trueshot.app.scenarios;

public class ScenarioBuilder
{
	public static TrueScenario build(String cipic, String scenario)
	{
	    TrueScenario currentScenario = null;
		switch (scenario)
                {
                    case "Scenario1":
                        currentScenario = new Scenario1(cipic);
                        break;
                    case "Scenario2":
                        currentScenario = new Scenario2(cipic);
                        break;
                    case "Scenario3":
                        currentScenario = new Scenario3(cipic);
                    //              currentScenario = new Scenario3();
                        break;
                    case "Scenario4":
                        currentScenario = new Scenario4(cipic);
                        break;
                    case "Scenario5":
                        currentScenario = new Scenario5(cipic);
                        break;
                    case "Scenario6":
                        currentScenario = new Scenario6(cipic);
                        break;
                    case "Scenario7":
                        currentScenario = new Scenario7(cipic);
                        break;
                    case "Scenario8":
                        currentScenario = new Scenario8(cipic);
                        break;
                    case "Scenario9":
                        currentScenario = new Scenario9(cipic);
                        break;
                    default:
                }
                return currentScenario;
	    }
}
