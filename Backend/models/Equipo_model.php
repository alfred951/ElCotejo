<?php
class Equipo_model extends CI_Model {
    public function __construct(){
        parent::__construct();
    }

    public function getEquipos(){
        $query = $this->db->get('Equipo');
        return $query->result();
    }

    public function getEquipo($capitan,$nombre,$idEquipo){
        $data = array();
        if(!empty($capitan)){
            $nickname = urldecode($capitan);
            $data['capitan'] = $capitan;
        }
        if(!empty($nombre)){
            $nombre = urldecode($nombre);
            $data['nombre'] = $nombre;
        }
        if (!empty($data)){
            $this->db->select('*');
            $this->db->where('nombre',$nombre);
            $this->db->from('Equipo');
            $result = $this->db->get()->result_array();
            if(empty($result)){
                $this->db->set($data);
                $this->db->insert('Equipo');
            }
            else{
                $this->db->set($data);
                $this->db->where('nombre',$nombre);
                $this->db->update('Equipo', $data);
            }
        }
        if(!empty($idEquipo)){
            $this->db->select('*');
            $this->db->where('idEquipo',$idEquipo);
            $this->db->from('Equipo');
            $result = $this->db->get()->result_array();
            return $result;
        }
        else{
            $this->db->select('*');
            $this->db->where('nombre',$nombre);
            $this->db->from('Equipo');
            $result = $this->db->get()->result_array();
            return $result;
        }
    }

    public function getIntegrantes($idEquipo,$nickname){
        $data = array();
        if(!empty($nickname)){
            $nickname = urldecode($nickname);
            $data['nickname'] = $nickname;
        }
        if (!empty($data)){
            $this->db->select('*');
            $this->db->where('idEquipo',$idEquipo);
            $this->db->where('nickname',$nickname);
            $this->db->from('Integrantes');
            $result = $this->db->get()->result_array();
            if(empty($result)){
                $nickname = urldecode($idEquipo);
                $data['idEquipo'] = $idEquipo;
                $this->db->set($data);
                $this->db->insert('Integrantes');
            }
        }
        $this->db->select('nickname');
        $this->db->from('Integrantes');
        $this->db->where('idEquipo',$idEquipo);
        $query = $this->db->get();
        return $query->result();
    }
}
?>
