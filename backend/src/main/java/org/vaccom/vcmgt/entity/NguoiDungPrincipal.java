package org.vaccom.vcmgt.entity;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NguoiDungPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private long id;
	
	private String tenDangNhap;
	
	private String hoVaTen;
	@JsonIgnore
	private String chucDanh;
	@JsonIgnore
	private String soDienThoai;
	@JsonIgnore
	private String email;
	@JsonIgnore
	private String matKhau;
	@JsonIgnore
	private long diaBanCoSoId;
	@JsonIgnore
	private long coSoYTeId;
	@JsonIgnore
	private int vaiTro;
	@JsonIgnore
	private boolean khoaTaiKhoan;
	@JsonIgnore
	private long nguoiTiemChungId;

	private Collection<? extends GrantedAuthority> authorities;

	public NguoiDungPrincipal(long id, String tenDangNhap, String hoVaTen, String chucDanh, String soDienThoai,
			String email, String matKhau, long diaBanCoSoId, long coSoYTeId, int vaiTro,
			boolean khoaTaiKhoan, long nguoiTiemChungId) {
		this.id = id;
		this.tenDangNhap = tenDangNhap;
		this.hoVaTen = hoVaTen;
		this.chucDanh = chucDanh;
		this.soDienThoai = soDienThoai;
		this.email = email;
		this.matKhau = matKhau;
		this.diaBanCoSoId = diaBanCoSoId;
		this.coSoYTeId = coSoYTeId;
		this.khoaTaiKhoan = khoaTaiKhoan;
		this.vaiTro = vaiTro;
		this.nguoiTiemChungId = nguoiTiemChungId;
	}

	public static NguoiDungPrincipal create(NguoiDung nguoiDung) {

		return new NguoiDungPrincipal(nguoiDung.getId(), nguoiDung.getTenDangNhap(), nguoiDung.getHoVaTen(),
				nguoiDung.getChucDanh(), nguoiDung.getSoDienThoai(), nguoiDung.getEmail(), nguoiDung.getMatKhau(),
				nguoiDung.getDiaBanCoSoId(), nguoiDung.getCoSoYTeId(), nguoiDung.getVaiTro(),
				nguoiDung.isKhoaTaiKhoan(), nguoiDung.getNguoiTiemChungId());
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return tenDangNhap;
	}

	@Override
	public String getPassword() {
		return matKhau;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		NguoiDungPrincipal that = (NguoiDungPrincipal) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	public String getChucDanh() {
		return chucDanh;
	}

	public void setChucDanh(String chucDanh) {
		this.chucDanh = chucDanh;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public long getDiaBanCoSoId() {
		return diaBanCoSoId;
	}

	public void setDiaBanCoSoId(long diaBanCoSoId) {
		this.diaBanCoSoId = diaBanCoSoId;
	}

	public long getCoSoYTeId() {
		return coSoYTeId;
	}

	public void setCoSoYTeId(long coSoYTeId) {
		this.coSoYTeId = coSoYTeId;
	}

	public boolean isKhoaTaiKhoan() {
		return khoaTaiKhoan;
	}

	public void setKhoaTaiKhoan(boolean khoaTaiKhoan) {
		this.khoaTaiKhoan = khoaTaiKhoan;
	}

	public int getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(int vaiTro) {
		this.vaiTro = vaiTro;
	}

	public long getNguoiTiemChungId() {
		return nguoiTiemChungId;
	}

	public void setNguoiTiemChungId(long nguoiTiemChungId) {
		this.nguoiTiemChungId = nguoiTiemChungId;
	}

	
}