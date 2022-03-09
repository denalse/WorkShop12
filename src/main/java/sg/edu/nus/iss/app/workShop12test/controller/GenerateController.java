package sg.edu.nus.iss.app.workShop12test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.nus.iss.app.workShop12test.exception.RandomNumberException;
import sg.edu.nus.iss.app.workShop12test.model.Generate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class GenerateController {
    private Logger logger = LoggerFactory.getLogger(GenerateController.class);

    @GetMapping("/")
    public String showGenerateForm(Model model) {
        Generate generate = new Generate();
        model.addAttribute("generateObj", generate);
        //return the html page "generatePage.html" the endpoint
        return "generatePage";
    }

    @PostMapping("/generate")
    public String generateNumbers(@ModelAttribute 
                Generate generate, Model model) {
            try {
                logger.info("Form the form" + generate.getNumberVal());
                int numOfRandNumbers = generate.getNumberVal();

                if (numOfRandNumbers > 30) {
                    throw new RandomNumberException();
                }
                
                String[] imgNumbers = {
                    "1.png", "2.png", "3.png", "4.png", "5.png",
                    "6.png", "7.png", "8.png", "9.png", "10.png",
                    "11.png", "12.png", "13.png", "14.png", "15.png",
                    "16.png", "17.png", "18.png", "19.png", "20.png",
                    "21.png", "22.png", "23.png", "24.png", "25.png",
                    "26.png", "27.png", "28.png", "29.png", "30.png"
                };

                List<String> selectedImg = new ArrayList<String>();
                Random randNum = new Random();
                Set<Integer> uniqueGeneratedRandNumSet = new LinkedHashSet<Integer>();

                while(uniqueGeneratedRandNumSet.size() < numOfRandNumbers) {
                    Integer resultofRandNumbers = 
                    randNum.nextInt(generate.getNumberVal() + 1);

                    uniqueGeneratedRandNumSet.add(resultofRandNumbers);
                }

                Iterator<Integer> it = uniqueGeneratedRandNumSet.iterator();
                Integer currentElem = null;

                while(it.hasNext()) {
                    currentElem = it.next();
                    logger.info("currentElem > " + currentElem);

                    selectedImg.add(imgNumbers[currentElem.intValue()]);
                }

                model.addAttribute("randNumsResult", selectedImg.toArray());
                model.addAttribute("numInputByUser", numOfRandNumbers);

                }catch(RandomNumberException e) {

                    model.addAttribute("errorMessage", "OMG exceed 30 already !");
                    return "error";
                }
                return "result";
    }

}
