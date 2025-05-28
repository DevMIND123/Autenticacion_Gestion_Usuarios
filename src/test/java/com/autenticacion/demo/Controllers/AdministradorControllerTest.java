// package com.autenticacion.demo.Controllers;

// import com.autenticacion.demo.Dto.AdministradorActualizarDTO;
// import com.autenticacion.demo.Dto.AdministradorRegistroDTO;
// import com.autenticacion.demo.Dto.AdministradorRespuestaDTO;
// import com.autenticacion.demo.Dto.CambioPasswordDTO;
// import com.autenticacion.demo.Services.AdministradorService;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest(AdministradorController.class)
// public class AdministradorControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private AdministradorService administradorService;

//     @Test
//     void registrarAdministradorTest() throws Exception {
//         AdministradorRegistroDTO dto = new AdministradorRegistroDTO();
//         AdministradorRespuestaDTO respuestaDTO = new AdministradorRespuestaDTO();

//         Mockito.when(administradorService.registrarAdministrador(any(AdministradorRegistroDTO.class)))
//                 .thenReturn(respuestaDTO);

//         mockMvc.perform(post("/api.retochimba.com/administradores")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\"nombre\":\"Admin\",\"email\":\"admin@example.com\"}"))
//                 .andExpect(status().isOk());
//     }

//     @Test
//     void obtenerIdAdministradorPorEmailTest() throws Exception {
//         Mockito.when(administradorService.obtenerIdAdministradorPorEmail("admin@example.com"))
//                 .thenReturn(1L);

//         mockMvc.perform(get("/api.retochimba.com/administradores/email/admin@example.com"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("1"));
//     }

//     @Test
//     void actualizarAdministradorTest() throws Exception {
//         AdministradorActualizarDTO dto = new AdministradorActualizarDTO();

//         Mockito.when(administradorService.actualizarAdministrador(eq(1L), any(AdministradorActualizarDTO.class)))
//                 .thenReturn(true);

//         mockMvc.perform(patch("/api.retochimba.com/administradores/actualizar/1")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\"nombre\":\"Nuevo Admin\"}"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("Administrador actualizado correctamente."));
//     }

//     @Test
//     void actualizarAdministradorNotFoundTest() throws Exception {
//         Mockito.when(administradorService.actualizarAdministrador(eq(1L), any(AdministradorActualizarDTO.class)))
//                 .thenReturn(false);

//         mockMvc.perform(patch("/api.retochimba.com/administradores/actualizar/1")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\"nombre\":\"Nuevo Admin\"}"))
//                 .andExpect(status().isNotFound())
//                 .andExpect(content().string("Administrador no encontrado."));
//     }

//     @Test
//     void eliminarAdministradorTest() throws Exception {
//         Mockito.doNothing().when(administradorService).eliminarAdministrador(1L);

//         mockMvc.perform(delete("/api.retochimba.com/administradores/eliminar/1"))
//                 .andExpect(status().isNoContent());
//     }

//     @Test
//     void cambiarPasswordTest() throws Exception {
//         CambioPasswordDTO dto = new CambioPasswordDTO();

//         Mockito.doNothing().when(administradorService).cambiarPassword(any(CambioPasswordDTO.class));

//         mockMvc.perform(patch("/api.retochimba.com/administradores/cambiar-password")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\"email\":\"admin@example.com\",\"nuevaPassword\":\"newPassword\"}"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("Contraseña actualizada correctamente."));
//     }
// }