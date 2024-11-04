import axios from 'axios';
import { Button } from 'primereact/button';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Toast } from 'primereact/toast';
import { useEffect, useRef, useState } from 'react';

const ProductTable = () => {
	interface Product {
		id: number | null;
		code: string;
		name: string;
		description: string;
		price: number;
	}

	const [products, setProducts] = useState<Product[]>([]);
	const [productDialog, setProductDialog] = useState(false);
	const [deleteDialog, setDeleteDialog] = useState(false);
	const [currentProduct, setCurrentProduct] = useState<Product | null>(null);
	const toast = useRef<Toast>(null);

	useEffect(() => {
		axios
			.get<Product[]>('http://localhost:8080/api/v1/products')
			.then((response) => setProducts(response.data))
			.catch((error) =>
				console.error('Erro ao carregar produtos:', error)
			);
	}, []);

	const openNew = () => {
		setCurrentProduct({
			id: null,
			code: '',
			name: '',
			description: '',
			price: 0,
		});
		setProductDialog(true);
	};

	const editProduct = (product: Product) => {
		setCurrentProduct({ ...product });
		setProductDialog(true);
	};

	const hideDialog = () => {
		setProductDialog(false);
		setCurrentProduct(null);
	};

	const saveProduct = () => {
		if (currentProduct) {
			if (currentProduct.id) {
				axios
					.put(
						`http://localhost:8080/api/v1/products/${currentProduct.id}`,
						currentProduct
					)
					.then((response) => {
						setProducts(
							products.map((p) =>
								p.id === response.data.id ? response.data : p
							)
						);
						toast.current?.show({
							severity: 'success',
							summary: 'Produto Atualizado',
						});
					})
					.catch((error) =>
						console.error('Erro ao atualizar produto:', error)
					);
			} else {
				axios
					.post(
						'http://localhost:8080/api/v1/products',
						currentProduct
					)
					.then((response) => {
						setProducts([...products, response.data]);
						toast.current?.show({
							severity: 'success',
							summary: 'Produto Criado',
						});
					})
					.catch((error) =>
						console.error('Erro ao criar produto:', error)
					);
			}
			setProductDialog(false);
		}
	};

	const confirmDeleteProduct = (product: Product) => {
		setCurrentProduct(product);
		setDeleteDialog(true);
	};

	const deleteProduct = () => {
		if (currentProduct) {
			axios
				.delete(
					`http://localhost:8080/api/v1/products/${currentProduct.id}`
				)
				.then(() => {
					setProducts(
						products.filter((p) => p.id !== currentProduct.id)
					);
					toast.current?.show({
						severity: 'error',
						summary: 'Produto Deletado',
					});
				})
				.catch((error) =>
					console.error('Erro ao deletar produto:', error)
				);
			setDeleteDialog(false);
		}
	};

	const actionBodyTemplate = (rowData: Product) => (
		<>
			<Button
				icon='pi pi-pencil'
				className='p-button-rounded p-button-success mr-2'
				onClick={() => editProduct(rowData)}
			/>
			<Button
				icon='pi pi-trash'
				className='p-button-rounded p-button-danger'
				onClick={() => confirmDeleteProduct(rowData)}
			/>
		</>
	);

	return (
		<div>
			<Toast ref={toast} />
			<Button
				label='Novo Produto'
				icon='pi pi-plus'
				className='p-mb-3'
				onClick={openNew}
			/>
			<DataTable value={products} responsiveLayout='scroll'>
				<Column field='id' header='ID' />
				<Column field='code' header='Código' />
				<Column field='name' header='Nome' />
				<Column field='description' header='Descrição' />
				<Column field='price' header='Preço' />
				<Column body={actionBodyTemplate} header='Ações' />
			</DataTable>

			<Dialog
				visible={productDialog}
				header='Detalhes do Produto'
				modal
				onHide={hideDialog}
				style={{ width: '450px' }}
				footer={
					<div>
						<Button
							label='Cancelar'
							icon='pi pi-times'
							onClick={hideDialog}
							className='p-button-text'
						/>
						<Button
							label='Salvar'
							icon='pi pi-check'
							onClick={saveProduct}
							autoFocus
						/>
					</div>
				}
			>
				<div className='p-fluid'>
					<div className='p-field'>
						<label htmlFor='code'>Código</label>
						<InputText
							id='code'
							value={currentProduct?.code || ''}
							onChange={(e) =>
								setCurrentProduct((prev) =>
									prev
										? { ...prev, code: e.target.value }
										: null
								)
							}
						/>
					</div>
					<div className='p-field'>
						<label htmlFor='name'>Nome</label>
						<InputText
							id='name'
							value={currentProduct?.name || ''}
							onChange={(e) =>
								setCurrentProduct((prev) =>
									prev
										? { ...prev, name: e.target.value }
										: null
								)
							}
						/>
					</div>
					<div className='p-field'>
						<label htmlFor='description'>Descrição</label>
						<InputText
							id='description'
							value={currentProduct?.description || ''}
							onChange={(e) =>
								setCurrentProduct((prev) =>
									prev
										? {
												...prev,
												description: e.target.value,
										  }
										: null
								)
							}
						/>
					</div>
					<div className='p-field'>
						<label htmlFor='price'>Preço</label>
						<InputText
							id='price'
							value={currentProduct?.price.toString() || ''}
							onChange={(e) =>
								setCurrentProduct((prev) =>
									prev
										? {
												...prev,
												price:
													parseFloat(
														e.target.value
													) || 0,
										  }
										: null
								)
							}
						/>
					</div>
				</div>
			</Dialog>

			<Dialog
				visible={deleteDialog}
				header='Confirmação'
				modal
				onHide={() => setDeleteDialog(false)}
				style={{ width: '350px' }}
				footer={
					<div>
						<Button
							label='Não'
							icon='pi pi-times'
							onClick={() => setDeleteDialog(false)}
							className='p-button-text'
						/>
						<Button
							label='Sim'
							icon='pi pi-check'
							onClick={deleteProduct}
							autoFocus
						/>
					</div>
				}
			>
				<p>
					Tem certeza que deseja deletar o produto{' '}
					<b>{currentProduct?.name}</b>?
				</p>
			</Dialog>
		</div>
	);
};

export default ProductTable;
