package RayTracing;

import java.io.IOException;
import RayTracing.RayTracer.RayTracerException;

/*
 * easier to test several .txt files
 * command line arguments should be input directory (with all relevant .txt files) 
 * and output directory (where you want the images to go), do not forget '\' at the end
 * optional image width and height
 * array "fileNames" contains the names of the .txt files you wish to render (all files should be in input directory)
 * image will be created with same name in output directory
 */
public class FileTester {

	public static void main(String[] args) throws RayTracerException {
		try {
			RayTracer tracer = new RayTracer();
			String fileNames[] = {"Pool", "Room1", "Room10", "Spheres", "Transparency", "Triangle", "Triangle2"};

			// Default values:
			tracer.imageWidth = 500;
			tracer.imageHeight = 500;

			if (args.length < 2){
				throw new RayTracerException(
						"Not enough arguments provided. Please specify an input scene file and an output image file for rendering.");
			}
			String InputDirectory = args[0];
			String outputDirectory = args[1];

			String sceneFileName;
			String outputFileName;

			if (args.length > 3) {
				tracer.imageWidth = Integer.parseInt(args[2]);
				tracer.imageHeight = Integer.parseInt(args[3]);
			}
			
			for (String file : fileNames){
				sceneFileName = InputDirectory.concat(file).concat(".txt");
				outputFileName = outputDirectory.concat(file).concat(".png");
				FileTester.createImage(tracer, sceneFileName, outputFileName);
			}
			System.out.println("finished rendering all files");
		} catch (RayTracerException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
		 System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


	}
		
		public static void createImage(RayTracer tracer, String input, String output) throws IOException, RayTracerException{
			// Parse scene file:
			tracer.parseScene(input);

			// Render scene:
			tracer.renderScene(output);

		}

}
