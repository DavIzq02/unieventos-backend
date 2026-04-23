package com.example.unieventos.services;

import com.example.unieventos.config.SecurityConfig;
import com.example.unieventos.models.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.UsuarioRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GitService gitService;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(Usuario nuevoUsuario) {

        if (nuevoUsuario.getNombre() == null || nuevoUsuario.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio");
        }

        if (nuevoUsuario.getCorreo() == null || nuevoUsuario.getCorreo().isEmpty()) {
            throw new RuntimeException("El correo es obligatorio");
        }
        nuevoUsuario.setActivo(true);
        nuevoUsuario.setUrlFoto("not defined");
        nuevoUsuario.setFechaDeCreacion(LocalDateTime.now());
        String passwordHash = passwordEncoder.encode(nuevoUsuario.getContrasena());
        nuevoUsuario.setContrasena(passwordHash);

        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario updateUsuario(Usuario usuarioModificado){
        Usuario usuario = usuarioRepository.findByCorreo(usuarioModificado.getCorreo()).orElseThrow(() ->
                new RuntimeException("Correo no registrado en el sistema")
        );

        if (!passwordEncoder.matches(usuarioModificado.getContrasena(), usuario.getContrasena())) {
            //La contraseña no coincide es decir que la cambio , codificamos la nueva contraseña y la guardamos
            String passwordHash = passwordEncoder.encode(usuarioModificado.getContrasena());
            usuarioModificado.setContrasena(passwordHash);
        }

        usuarioModificado.setFechaDeModificacion(LocalDateTime.now());
        return usuarioRepository.save(usuarioModificado);
    }

    public Usuario login(String correo, String contrasena) {

        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() ->
                new RuntimeException("Usuario no existe")
        );

        if(!usuario.getActivo()){
            throw new RuntimeException("La cuenta no está activa");
        }

        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        return usuario;
    }

    public Integer deleteUsuario(Integer idUsuario){
        Usuario u = new Usuario(idUsuario);
        try {
            usuarioRepository.delete(u);
            return 200;
        } catch (Exception e) {
            return 500;
        }
    }

    public Integer cambiarEstadoUsuario(Integer idUsuario,Boolean estado){
        try {
            Integer filasAfectadas =  usuarioRepository.updateEstado(idUsuario, estado);
            if(filasAfectadas == 1){
                return 200;//codigo interno de transaccion exitosa
            }else if(filasAfectadas == 0){
                return 404;//codigo interno de transaccion exitosa pero sin resultados
            }else{
                return 500;//codigo interno de error
            }
        }catch (Exception e){
            System.out.println("Error al cambiar el estado del usuario "+e.toString());
            return 500;//codigo interno de error
        }

    }

    public Usuario crearUsuarioPostLogin(Usuario nuevoUsuario, MultipartFile cargaUtil) throws IOException {

        Optional<Usuario> usuarioExistente = usuarioRepository.findByCodigo(nuevoUsuario.getCodigo());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El código ya está registrado");
        }

        String passwordHash = passwordEncoder.encode(nuevoUsuario.getContrasena());
        nuevoUsuario.setContrasena(passwordHash);

        if (cargaUtil != null && !cargaUtil.isEmpty()) {
            try {
                String carpeta = "usuarios";
                String nombreOriginal = cargaUtil.getOriginalFilename();

                String extension = "";
                if (nombreOriginal != null && nombreOriginal.contains(".")) {
                    extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
                }

                String nombreArhivo = "foto_perfil_de_"+nuevoUsuario.getCodigo()+extension;
                String urlCdn = gitService.upLoadFile(carpeta,nombreArhivo,cargaUtil,false,"");
                nuevoUsuario.setUrlFoto(urlCdn);
            } catch (Exception e) {
                nuevoUsuario.setUrlFoto("not defined");
                throw new RuntimeException(e);

            }
        } else {
            nuevoUsuario.setUrlFoto("not defined");
        }

        nuevoUsuario.setFechaDeCreacion(LocalDateTime.now());
        nuevoUsuario.setActivo(true);

        int rolId = (nuevoUsuario.getComunidad().getId() != 5) ? 4 : 3;
        nuevoUsuario.setRol(new Rol(rolId));

        return usuarioRepository.save(nuevoUsuario);
    }

    public Integer updateFotoPerfil(Usuario usuario, MultipartFile cargaUtil) throws IOException {

        if (cargaUtil != null && !cargaUtil.isEmpty()) {
            try {
                String carpeta = "usuarios";

                String extension = "";
                String nombreOriginal = cargaUtil.getOriginalFilename();

                if (nombreOriginal != null && nombreOriginal.contains(".")) {
                    extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
                }

                String nombreArhivo = "foto_perfil_de_"+usuario.getCodigo()+extension;
                String nombreArchivoAnterior = extraerNombreArchivoDesdeUrl(usuario.getUrlFoto());
                String urlCdn = gitService.upLoadFile(carpeta,nombreArhivo,cargaUtil,true,nombreArchivoAnterior);
                usuario.setUrlFoto(urlCdn);
                System.out.println("Cambiando "+nombreArchivoAnterior+" por "+nombreArhivo);
                usuarioRepository.updateUrlFoto(usuario.getId(), usuario.getUrlFoto());
            } catch (Exception e) {
                throw new RuntimeException("Ocurrió un error actualizando la foto "+e.toString());
            }
        } else {
            throw new RuntimeException("Imagen no leida, no se actualizó la foto");
        }

        return 200;
    }

    public String extraerNombreArchivoDesdeUrl(String url) {
        if (url == null || url.isEmpty()) return null;

        String[] partes = url.split("/");
        return partes[partes.length - 1];
    }


}