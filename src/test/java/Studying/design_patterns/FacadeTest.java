package Studying.design_patterns;

import studying.design_patterns.facade.HeatedSwimmingPoolFacade;
import studying.design_patterns.implementations.facade_implementation.HeatingEngine;
import studying.design_patterns.implementations.facade_implementation.HydroMassageEngine;
import studying.design_patterns.implementations.facade_implementation.Light;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class FacadeTest {
    // Instantiating single components
    HydroMassageEngine hydroMassageEngine = new HydroMassageEngine();
    Light light = new Light();
    HeatingEngine heatingEngine = new HeatingEngine();

    // Instantiating the Facade
    HeatedSwimmingPoolFacade heatedSwimmingPoolFacade = new HeatedSwimmingPoolFacade(hydroMassageEngine, light, heatingEngine);

    @Test
    public void correctRomanticSettingsTest() {
        heatedSwimmingPoolFacade.setRomanticAtmosphere();
        Assertions.assertEquals(hydroMassageEngine.getIntensityValue(), 5);
        Assertions.assertEquals(light.getLightColor(), "purple");
    }

    @Test
    public void correctMuscleReliefSettingsTest() {
        heatedSwimmingPoolFacade.activateMuscleReliefSetting();
        Assertions.assertEquals(hydroMassageEngine.getIntensityValue(), 50);
        Assertions.assertEquals(heatingEngine.getTemperature(), 20);
    }
}
