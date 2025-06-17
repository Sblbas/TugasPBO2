package src.controller;

import src.service.VillaService;
import Tugas2.Response;

public class VillaController {

    private VillaService villaService;

    public VillaController() {
        this.villaService = new VillaService();
    }

    public Response getAllVillas() {
        try {
            String jsonData = villaService.getAllVillasAsJson();
            return new Response(200, "OK", jsonData);
        } catch (Exception e) {
            return new Response(500, "Internal Server Error", "{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}